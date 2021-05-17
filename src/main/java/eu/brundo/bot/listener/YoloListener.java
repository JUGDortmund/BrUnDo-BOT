package eu.brundo.bot.listener;

import eu.brundo.bot.data.BadnessManager;
import eu.brundo.bot.util.BrundoUtils;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class YoloListener extends ListenerAdapter {

    public YoloListener() {
    }

    @Override
    public void onMessageReceived(final MessageReceivedEvent event) {
        final String message = event.getMessage().getContentDisplay();

        if (message.toLowerCase().contains("yolo")) {
            BadnessManager.getInstance().increase(BrundoUtils.getUserName(event.getMember()));
            BrundoUtils.sendMessage(event.getChannel(), "yolo.answer");
        }
    }
}
