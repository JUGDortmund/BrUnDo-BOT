package eu.brundo.bot.achievements;

import eu.brundo.bot.MongoConnector;
import eu.brundo.bot.services.ChannelSessionService;
import eu.brundo.bot.util.TimeUtils;
import net.dv8tion.jda.api.entities.Member;

import java.time.Duration;
import java.time.ZonedDateTime;

public class SitzfleischAchievement extends AbstractCheckableAchievement {

    private final ChannelSessionService channelSessionService;

    public SitzfleischAchievement(final MongoConnector mongoConnector) {
        super("Sitzfleisch", 10);
        this.channelSessionService = new ChannelSessionService(mongoConnector);
    }

    @Override
    public boolean achived(final Member member) {
        return channelSessionService.getAllSessionsForUser(member).stream()
                .filter(session -> {
                    final ZonedDateTime startTime = TimeUtils.convertToLocalDateTime(session.getStartTime());
                    final ZonedDateTime endTime = TimeUtils.convertToLocalDateTime(session.getEndTime());
                    final Duration duration = Duration.between(startTime, endTime);
                    return duration.toHours() > 4;
                })
                .findAny()
                .isPresent();
    }
}
