package eu.brundo.bot.achievements;

import eu.brundo.bot.MongoConnector;
import eu.brundo.bot.data.BrundoVoiceChannel;
import eu.brundo.bot.services.ChannelSessionService;
import net.dv8tion.jda.api.entities.Member;

import java.util.Objects;

public class FirstTimeInTreffpunktAchievement extends AbstractCheckableAchievement {

    private final ChannelSessionService channelSessionService;

    public FirstTimeInTreffpunktAchievement(final MongoConnector mongoConnector) {
        super("FirstTimeInTreffpunkt", "FirstContact");
        this.channelSessionService = new ChannelSessionService(mongoConnector);
    }

    @Override
    public boolean achived(final Member member) {
        return channelSessionService.getAllSessionsForUser(member)
                .stream()
                .map(s -> s.getChannelId())
                .map(id -> member.getJDA().getVoiceChannelById(id))
                .filter(c -> Objects.equals(BrundoVoiceChannel.TREFFPUNKT.getName(), c.getName()))
                .findAny()
                .isPresent();
    }

    @Override
    public String getDescription() {
        return "Du bist das erste mal im 'Treffpunkt' gewesen.";
    }
}
