package eu.brundo.bot.commands;

import eu.brundo.bot.util.ChannelMessageSender;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;

public abstract class AbstractCommand extends ListenerAdapter implements Comparable<AbstractCommand> {

    private final static Logger LOG = LoggerFactory.getLogger(AbstractCommand.class);

    public final static String EXCLAMATION_MARK = "!";

    private final String command;

    private static final Random random = new Random(System.currentTimeMillis());

    public AbstractCommand(final String command) {
        this.command = Objects.requireNonNull(command);
    }

    protected abstract void onCommand(MessageReceivedEvent event);

    public abstract String getHelp();

    public abstract CommandCategories getCategory();

    @Override
    public void onMessageReceived(final MessageReceivedEvent event) {
        final String message = event.getMessage().getContentDisplay();
        if (message.toLowerCase().startsWith((EXCLAMATION_MARK + command).toLowerCase())) {
            if (isAllowed(event.getMember(), event.getChannel())) {
                LOG.info("User {} executes command {}", getUserName(event), command);
                event.getChannel().sendTyping().complete();
                onCommand(event);
            } else {
                LOG.info("User {} is not allowed to execute command {}", getUserName(event), command);
                event.getChannel().sendMessage("Aktuell hast du keine Berechtigung diesen Befehl auszuführen.").complete();
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

    public static String getUserName(final MessageReceivedEvent event) {
        Objects.requireNonNull(event);
        return Optional.of(event).map(e -> e.getMessage())
                .map(message -> message.getMember())
                .map(member -> getUserName(member))
                .orElse("unknown");
    }

    public static String getUserName(final Member member) {
        Objects.requireNonNull(member);
        final String effectiveName = member.getEffectiveName();
        final String nickName = member.getNickname();
        return Optional.ofNullable(nickName).filter(n -> n.length() > 0).orElse(effectiveName);
    }

    public void sendMessage(final MessageChannel channel, final String messageKey, final Object... values) {
        ChannelMessageSender.sendMessage(channel, messageKey, values);
    }

    public void sendTranslatedMessage(final MessageChannel channel, final String message) {
        ChannelMessageSender.sendTranslatedMessage(channel, message);
    }

    @Override
    public int compareTo(@NotNull final AbstractCommand o) {
        if (o == null) {
            return 1;
        }
        return getCategory().compareTo(o.getCategory());
    }
}
