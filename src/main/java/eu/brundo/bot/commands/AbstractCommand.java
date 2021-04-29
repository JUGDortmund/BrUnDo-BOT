package eu.brundo.bot.commands;

import eu.brundo.bot.util.BottiResourceBundle;
import eu.brundo.bot.util.BrundoUtils;
import eu.brundo.bot.util.ChannelMessageSender;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public abstract class AbstractCommand extends ListenerAdapter implements Comparable<AbstractCommand> {

    private final static Logger LOG = LoggerFactory.getLogger(AbstractCommand.class);

    public final static String EXCLAMATION_MARK = "!";

    private final String command;

    private static final Random random = new Random(System.currentTimeMillis());

    private final static ExecutorService COMMAND_EXECUTOR_SERVICE = Executors.newCachedThreadPool();

    public AbstractCommand(final String command) {
        this.command = Objects.requireNonNull(command);
    }

    protected abstract void onCommand(MessageReceivedEvent event);

    public String getHelp() {
        return BottiResourceBundle.getMessage("command." + command + ".help");
    }

    public abstract CommandCategories getCategory();

    @Override
    public void onMessageReceived(final MessageReceivedEvent event) {
        final String message = event.getMessage().getContentDisplay();
        if (message.toLowerCase().startsWith((EXCLAMATION_MARK + command).toLowerCase())) {
            if (isAllowed(event.getMember(), event.getChannel())) {
                LOG.info("User {} executes command {}", BrundoUtils.getUserName(event), command);
                sendTyping(event.getChannel());
                COMMAND_EXECUTOR_SERVICE.submit(() -> onCommand(event));
            } else {
                LOG.info("User {} is not allowed to execute command {}", BrundoUtils.getUserName(event), command);
                sendMessage(event.getChannel(), "commands.noPermission");
            }
        }
    }

    public String getCommand() {
        return command;
    }

    public boolean isAllowed(final Member overviewRequester, final MessageChannel channel) {
        return true;
    }

    protected boolean isBottiAdmin(final Member overviewRequester) {
        return overviewRequester.getRoles().stream().filter(role -> Objects.equals(role.getName(), "Botti-Admin")).findAny().isPresent();
    }

    protected boolean isAdmin(final Member overviewRequester) {
        return overviewRequester.getPermissions().contains(Permission.ADMINISTRATOR);
    }

    public static <T> T getRandomEntry(final List<T> list) {
        return list.get(random.nextInt(list.size()));
    }

    public static <T> T getAndRemoveRandomEntry(final List<T> list) {
        return list.remove(random.nextInt(list.size()));
    }

    public Message sendMessage(final MessageChannel channel, final String messageKey, final Object... values) {
        return ChannelMessageSender.sendMessage(channel, messageKey, values);
    }

    public Message sendTranslatedMessage(final MessageChannel channel, final String message) {
        return ChannelMessageSender.sendTranslatedMessage(channel, message);
    }

    public void sendTyping(final MessageChannel channel) {
        channel.sendTyping().complete();
    }

    public String translate(final String messageKey, final Object... values) {
        return BottiResourceBundle.getMessage(messageKey, values);
    }

    public void sleep(final long ms) {
        try {
            Thread.sleep(ms);
        } catch (final InterruptedException e) {
            throw new RuntimeException("error in sleep", e);
        }
    }

    @Override
    public int compareTo(final AbstractCommand o) {
        if (o == null) {
            return 1;
        }
        return getCategory().compareTo(o.getCategory());
    }
}
