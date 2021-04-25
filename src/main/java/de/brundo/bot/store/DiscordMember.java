package de.brundo.bot.store;

import dev.morphia.annotations.Entity;
import dev.morphia.annotations.Id;
import org.bson.types.ObjectId;

@Entity("members")
public class DiscordMember {

    @Id
    private ObjectId id;

    private String discordId;

    private String nickname;

    private String effectiveName;

    private boolean collectingDataAllowed;

    public ObjectId getId() {
        return id;
    }

    public String getDiscordId() {
        return discordId;
    }

    public String getNickname() {
        return nickname;
    }

    public String getEffectiveName() {
        return effectiveName;
    }

    public boolean isCollectingDataAllowed() {
        return collectingDataAllowed;
    }

    public void setId(final ObjectId id) {
        this.id = id;
    }

    public void setDiscordId(final String discordId) {
        this.discordId = discordId;
    }

    public void setNickname(final String nickname) {
        this.nickname = nickname;
    }

    public void setEffectiveName(final String effectiveName) {
        this.effectiveName = effectiveName;
    }

    public void setCollectingDataAllowed(final boolean collectingDataAllowed) {
        this.collectingDataAllowed = collectingDataAllowed;
    }
}
