package eu.brundo.bot.achievements;

import eu.brundo.bot.MongoConnector;
import eu.brundo.bot.services.ChannelSessionService;
import eu.brundo.bot.util.TimeUtils;
import net.dv8tion.jda.api.entities.Member;

import java.time.Month;

public class SpielInDenMaiAchievment extends AbstractCheckableAchievement {

    private final ChannelSessionService channelSessionService;

    public SpielInDenMaiAchievment(final MongoConnector mongoConnector) {
        super("SpielInDenMai", 10);
        this.channelSessionService = new ChannelSessionService(mongoConnector);
    }

    @Override
    public boolean achived(final Member member) {
        return channelSessionService.getAllSessionsForUser(member).stream()
                .filter(session -> TimeUtils.convertToLocalDateTime(session.getStartTime()).getHour() <= 23)
                .filter(session -> TimeUtils.convertToLocalDateTime(session.getEndTime()).getHour() >= 0)
                .filter(session -> TimeUtils.convertToLocalDateTime(session.getEndTime()).getMonth() == Month.MAY)
                .filter(session -> TimeUtils.convertToLocalDateTime(session.getEndTime()).getDayOfMonth() == 1)
                .findAny()
                .isPresent();
    }
}
