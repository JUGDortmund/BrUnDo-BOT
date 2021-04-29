package eu.brundo.bot.achievements;

import eu.brundo.bot.MongoConnector;
import eu.brundo.bot.services.ChannelSessionService;
import eu.brundo.bot.util.TimeUtils;
import net.dv8tion.jda.api.entities.Member;

public class MidnightPlayerAchievement extends AbstractCheckableAchievement {

    private final ChannelSessionService channelSessionService;

    public MidnightPlayerAchievement(final MongoConnector mongoConnector) {
        super("MidnightPlayer", 10);
        this.channelSessionService = new ChannelSessionService(mongoConnector);
    }

    @Override
    public boolean achived(final Member member) {
        return channelSessionService.getAllSessionsForUser(member).stream()
                .filter(session -> TimeUtils.convertToLocalDateTime(session.getStartTime()).getDayOfMonth() != TimeUtils.convertToLocalDateTime(session.getEndTime()).getDayOfMonth())
                .findAny().isPresent();
    }
}
