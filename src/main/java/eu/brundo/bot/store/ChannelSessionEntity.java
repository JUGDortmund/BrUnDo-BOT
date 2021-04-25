package eu.brundo.bot.store;

import dev.morphia.annotations.Entity;
import dev.morphia.annotations.Id;
import dev.morphia.annotations.Reference;
import org.bson.types.ObjectId;

import java.util.Date;

@Entity("voiceChannelSession")
public class ChannelSessionEntity {

    @Id
    private ObjectId id;

    @Reference
    private MemberEntity member;

    private String channelId;

    private Date startTime;

    private Date endTime;

    public ObjectId getId() {
        return id;
    }

    public void setId(final ObjectId id) {
        this.id = id;
    }

    public MemberEntity getMember() {
        return member;
    }

    public void setMember(final MemberEntity member) {
        this.member = member;
    }

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(final String channelId) {
        this.channelId = channelId;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(final Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(final Date endTime) {
        this.endTime = endTime;
    }
}
