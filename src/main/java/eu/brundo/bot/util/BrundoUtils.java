package eu.brundo.bot.util;

import eu.brundo.bot.data.BrundoVoiceChannel;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Member;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class BrundoUtils {

    public static List<Member> getAllMembersInVoiceChannels(final JDA jda) {
        return Arrays.asList(BrundoVoiceChannel.values()).stream()
                .map(c -> jda.getVoiceChannelById(c.getId()))
                .filter(c -> c != null)
                .flatMap(c -> c.getMembers().stream())
                .collect(Collectors.toList());
    }
}
