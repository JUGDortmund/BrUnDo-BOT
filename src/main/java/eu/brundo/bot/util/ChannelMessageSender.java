package eu.brundo.bot.util;

import net.dv8tion.jda.api.entities.MessageChannel;

public class ChannelMessageSender {

    public static void sendMessage(final MessageChannel channel, final String messageKey, final Object... values) {
        final String message = BottiResourceBundle.getMessage(messageKey, values);
        channel.sendMessage(message).complete();
    }

}
