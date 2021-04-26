package eu.brundo.bot.entities;

import dev.morphia.annotations.Entity;
import dev.morphia.annotations.Id;
import dev.morphia.annotations.Reference;
import org.bson.types.ObjectId;

import java.util.Date;

@Entity("achievementEntity")
public class AchievementEntity implements IdBasedEntity, WithMemberRelation {

    @Id
    private ObjectId id;

    @Reference
    private MemberEntity member;

    private String achievementId;

    private Date achived;

    @Override
    public ObjectId getId() {
        return id;
    }

    @Override
    public void setId(final ObjectId id) {
        this.id = id;
    }

    @Override
    public MemberEntity getMember() {
        return member;
    }

    @Override
    public void setMember(final MemberEntity member) {
        this.member = member;
    }

    public String getAchievementId() {
        return achievementId;
    }

    public void setAchievementId(final String achievementId) {
        this.achievementId = achievementId;
    }

    public Date getAchived() {
        return achived;
    }

    public void setAchived(final Date achived) {
        this.achived = achived;
    }
}
