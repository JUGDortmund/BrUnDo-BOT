package eu.brundo.bot.util;

import eu.brundo.bot.data.BrundoVoiceChannel;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

public class BrundoUtils {

    public static List<Member> getAllMembersInVoiceChannels(final JDA jda) {
        return Arrays.asList(BrundoVoiceChannel.values()).stream()
                .map(c -> jda.getVoiceChannelById(c.getId()))
                .filter(c -> c != null)
                .flatMap(c -> c.getMembers().stream())
                .collect(Collectors.toList());
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
