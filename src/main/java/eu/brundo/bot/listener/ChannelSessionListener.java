package eu.brundo.bot.listener;

import eu.brundo.bot.MongoConnector;
import eu.brundo.bot.services.ChannelSessionService;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceUpdateEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import javax.annotation.Nonnull;

public class ChannelSessionListener extends ListenerAdapter {

    private final ChannelSessionService channelSessionService;

    public ChannelSessionListener(final MongoConnector mongoConnector) {
        channelSessionService = new ChannelSessionService(mongoConnector);
    }

    @Override
    public void onGuildVoiceUpdate(@Nonnull final GuildVoiceUpdateEvent event) {
        final VoiceChannel channelJoined = event.getChannelJoined();
        final VoiceChannel channelLeft = event.getChannelLeft();
        final Member member = event.getEntity();
        if (member != null) {
            if (channelLeft != null) {
                channelSessionService.onMemberLeavesChannel(channelLeft, member);
            }
            if (channelJoined != null) {
                channelSessionService.onMemberJoinsChannel(channelJoined, member);
            }
        }
    }
}
