package eu.brundo.bot.achievements;

import eu.brundo.bot.MongoConnector;
import eu.brundo.bot.services.ChannelSessionService;
import eu.brundo.bot.util.TimeUtils;
import net.dv8tion.jda.api.entities.Member;

import java.time.Duration;

public class NightWatchAchievement extends AbstractCheckableAchievement {

    private final ChannelSessionService channelSessionService;

    public NightWatchAchievement(final MongoConnector mongoConnector) {
        super("NightWatch", 10);
        this.channelSessionService = new ChannelSessionService(mongoConnector);
    }

    @Override
    public boolean achived(final Member member) {
        return channelSessionService.getAllSessionsForUser(member).stream()
                .filter(session -> TimeUtils.convertToLocalDateTime(session.getStartTime()).getHour() > 22)
                .filter(session -> Duration.between(TimeUtils.convertToLocalDateTime(session.getStartTime()), TimeUtils.convertToLocalDateTime(session.getEndTime())).toHours() > 4)
                .findAny()
                .isPresent();
    }
}
