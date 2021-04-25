package de.brundo.bot;

import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;
import java.util.Optional;

public abstract class AbstractCommand extends ListenerAdapter {

    private final static Logger LOG = LoggerFactory.getLogger(AbstractCommand.class);

    public final static String EXCLAMATION_MARK = "!";

    private final String command;

    public AbstractCommand(final String command) {
        this.command = Objects.requireNonNull(command);
    }

    protected abstract void onCommand(MessageReceivedEvent event);

    public abstract String getHelp();

    @Override
    public void onMessageReceived(final MessageReceivedEvent event) {
        final String message = event.getMessage().getContentDisplay();
        if (message.startsWith(EXCLAMATION_MARK + command)) {
            if (isAllowed(event.getMember(), event.getChannel())) {
                LOG.info("User {} executes command {}", getUserName(event), command);
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
}
