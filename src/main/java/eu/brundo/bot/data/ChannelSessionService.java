package eu.brundo.bot.data;

import eu.brundo.bot.MongoConnector;
import eu.brundo.bot.repositories.ChannelSessionRepository;
import eu.brundo.bot.store.ChannelSessionEntity;
import eu.brundo.bot.store.MemberEntity;
import net.dv8tion.jda.api.entities.GuildChannel;
import net.dv8tion.jda.api.entities.Member;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ChannelSessionService {

    private final static Logger LOG = LoggerFactory.getLogger(ChannelSessionService.class);

    private final Map<String, ChannelJoin> memberToChannelMap;

    private final ChannelSessionRepository channelSessionRepository;

    private final MemberService memberService;

    public ChannelSessionService(final MongoConnector mongoConnector) {
        this.memberToChannelMap = Collections.synchronizedMap(new HashMap<>());
        this.channelSessionRepository = new ChannelSessionRepository(mongoConnector);
        this.memberService = new MemberService(mongoConnector);
    }

    public void onMemberJoinsChannel(final GuildChannel channel, final Member member) {
        Objects.requireNonNull(channel);
        Objects.requireNonNull(member);
        LOG.info("User " + member.getEffectiveName() + " has joined channel " + channel.getName());
        if (memberService.isCollectingDataAllowed(member)) {
            this.memberToChannelMap.put(member.getId(), new ChannelJoin(channel.getId()));
        }
    }

    public void onMemberLeavesChannel(final GuildChannel channel, final Member member) {
        Objects.requireNonNull(channel);
        Objects.requireNonNull(member);
        LOG.info("User " + member.getEffectiveName() + " has left channel " + channel.getName());
        if (memberService.isCollectingDataAllowed(member)) {
            final ChannelJoin channelJoin = memberToChannelMap.get(member.getId());
            if (channelJoin != null && Objects.equals(channelJoin.getChannelId(), channel.getId())) {
                final ChannelSessionEntity entity = channelSessionRepository.createEntity();
                entity.setChannelId(channel.getId());
                entity.setStartTime(Date.from(channelJoin.getTimestamp().toInstant()));
                entity.setEndTime(Date.from(ZonedDateTime.now(ZoneId.of("Europe/Berlin")).toInstant()));
                final MemberEntity memberEntity = memberService.getOrCreateMemberEntity(member);
                entity.setMember(memberEntity);
                channelSessionRepository.save(entity);
            }
        }
    }

    private class ChannelJoin {
        private final ZonedDateTime timestamp;

        private final String channelId;

        public ChannelJoin(final String channelId) {
            this.channelId = channelId;
            this.timestamp = ZonedDateTime.now(ZoneId.of("Europe/Berlin"));
        }

        public ZonedDateTime getTimestamp() {
            return timestamp;
        }

        public String getChannelId() {
            return channelId;
        }
    }
}
