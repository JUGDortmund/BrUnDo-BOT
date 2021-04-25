package eu.brundo.bot.repositories;

import dev.morphia.query.experimental.filters.Filters;
import eu.brundo.bot.MongoConnector;
import eu.brundo.bot.entities.MemberEntity;
import net.dv8tion.jda.api.entities.Member;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class MemberRepository {

    private final MongoConnector mongoConnector;

    public MemberRepository(final MongoConnector mongoConnector) {
        this.mongoConnector = Objects.requireNonNull(mongoConnector);
    }

    public Optional<MemberEntity> findMemberByDiscordId(final String discordId) {
        final MemberEntity foundMember = mongoConnector.getDatastore()
                .find(MemberEntity.class)
                .filter(Filters.eq("discordId", discordId))
                .first();
        return Optional.ofNullable(foundMember);
    }

    public List<MemberEntity> findAll() {
        return mongoConnector.getDatastore().find(MemberEntity.class).iterator().toList();
    }

    public MemberEntity createNewEntity(final Member sender) {
        final MemberEntity discordMember = new MemberEntity();
        discordMember.setDiscordId(sender.getId());
        discordMember.setEffectiveName(sender.getEffectiveName());
        discordMember.setNickname(sender.getNickname());
        return discordMember;
    }

    public MemberEntity createAndSaveNewEntity(final Member sender) {
        final MemberEntity discordMember = new MemberEntity();
        discordMember.setDiscordId(sender.getId());
        discordMember.setEffectiveName(sender.getEffectiveName());
        discordMember.setNickname(sender.getNickname());
        save(discordMember);
        return discordMember;
    }

    public void save(final MemberEntity member) {
        Objects.requireNonNull(member);
        if (member.getId() == null) {
            mongoConnector.getDatastore().save(member);
        } else {
            mongoConnector.getDatastore().merge(member);
        }
    }
}
