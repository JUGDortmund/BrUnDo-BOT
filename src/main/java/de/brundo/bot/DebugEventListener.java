package de.brundo.bot;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DebugEventListener extends ListenerAdapter {

    private final static Logger LOG = LoggerFactory.getLogger(DebugEventListener.class);

    @Override
    public void onMessageReceived(final MessageReceivedEvent event) {
        final String message = event.getMessage().getContentDisplay();
        final String user = AbstractCommand.getUserName(event);
        LOG.info("User '{}' sendet Nachricht '{}'", user, message);
    }
}
