package eu.brundo.bot.achievements;

import eu.brundo.bot.MongoConnector;
import eu.brundo.bot.data.BrundoVoiceChannel;
import eu.brundo.bot.services.ChannelSessionService;
import net.dv8tion.jda.api.entities.Member;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PlayedAtAllTablesAchievement extends AbstractCheckableAchievement {

    private final ChannelSessionService channelSessionService;

    public PlayedAtAllTablesAchievement(final MongoConnector mongoConnector) {
        super("PlayedAtAllTables", 10);
        this.channelSessionService = new ChannelSessionService(mongoConnector);
    }

    @Override
    public boolean achived(final Member member) {
        final List<String> allUsedChannelIds = channelSessionService.getAllSessionsForUser(member).stream()
                .map(session -> session.getChannelId())
                .collect(Collectors.toList());
        return Stream.of(BrundoVoiceChannel.TISCH_1, BrundoVoiceChannel.TISCH_2, BrundoVoiceChannel.TISCH_3, BrundoVoiceChannel.TISCH_4, BrundoVoiceChannel.TISCH_5)
                .map(channel -> channel.getId())
                .filter(id -> !allUsedChannelIds.contains(id))
                .count() == 0;
    }

}
