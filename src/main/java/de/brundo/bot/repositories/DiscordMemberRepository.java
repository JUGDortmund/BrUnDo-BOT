package de.brundo.bot.repositories;

import de.brundo.bot.MongoConnector;
import de.brundo.bot.store.DiscordMember;
import dev.morphia.query.experimental.filters.Filters;
import net.dv8tion.jda.api.entities.Member;

import java.util.Objects;
import java.util.Optional;

public class DiscordMemberRepository {

    private final MongoConnector mongoConnector;

    public DiscordMemberRepository(final MongoConnector mongoConnector) {
        this.mongoConnector = Objects.requireNonNull(mongoConnector);
    }

    public Optional<DiscordMember> findMemberByDiscordId(final String discordId) {
        final DiscordMember foundMember = mongoConnector.getDatastore()
                .find(DiscordMember.class)
                .filter(Filters.eq("discordId", discordId))
                .first();
        return Optional.ofNullable(foundMember);
    }

    public DiscordMember createNewEntity(final Member sender) {
        final DiscordMember discordMember = new DiscordMember();
        discordMember.setDiscordId(sender.getId());
        discordMember.setEffectiveName(sender.getEffectiveName());
        discordMember.setNickname(sender.getNickname());
        return discordMember;
    }

    public void save(final DiscordMember member) {
        Objects.requireNonNull(member);
        if (member.getId() == null) {
            mongoConnector.getDatastore().save(member);
        } else {
            mongoConnector.getDatastore().merge(member);
        }
    }
}
