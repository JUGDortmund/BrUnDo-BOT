package eu.brundo.bot.util;

import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.PrivateChannel;

import java.util.Objects;

public class ChannelMessageSender {

    public static Message sendMessage(final MessageChannel channel, final String messageKey, final Object... values) {
        final String message = BottiResourceBundle.getMessage(messageKey, values);
        return sendTranslatedMessage(channel, message);
    }

    public static Message sendTranslatedMessage(final MessageChannel channel, final String message) {
        Objects.requireNonNull(channel);
        return channel.sendMessage(message).complete();
    }

    public static Message sendTranslatedDirectMessage(final Member member, final String message) {
        Objects.requireNonNull(member);
        final PrivateChannel privateChannel = member.getUser().openPrivateChannel().complete();
        return sendTranslatedMessage(privateChannel, message);
    }

}
