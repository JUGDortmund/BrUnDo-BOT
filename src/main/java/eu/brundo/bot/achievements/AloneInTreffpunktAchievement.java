package eu.brundo.bot.achievements;

import eu.brundo.bot.MongoConnector;
import eu.brundo.bot.data.BrundoVoiceChannel;
import eu.brundo.bot.services.ChannelSessionService;
import net.dv8tion.jda.api.entities.Member;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class AloneInTreffpunktAchievement extends AbstractCheckableAchievement {

    private final ChannelSessionService channelSessionService;

    public AloneInTreffpunktAchievement(final MongoConnector mongoConnector) {
        super("AlleinInTreffpunkt", 10);
        this.channelSessionService = new ChannelSessionService(mongoConnector);
    }

    @Override
    public boolean achived(final Member member) {
        final List<Member> members = Optional.ofNullable(member.getJDA())
                .map(jda -> jda.getVoiceChannelById(BrundoVoiceChannel.TREFFPUNKT.getId()))
                .map(voiceChannel -> voiceChannel.getMembers())
                .orElse(Collections.emptyList());
        return members.size() == 1 && members.contains(member);
    }
}
