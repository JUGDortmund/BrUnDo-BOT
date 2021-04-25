package eu.brundo.bot.services;

import eu.brundo.bot.MongoConnector;
import eu.brundo.bot.entities.MemberEntity;
import eu.brundo.bot.repositories.MemberRepository;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Member;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

public class MemberService {

    private final MemberRepository memberRepository;

    public MemberService(final MongoConnector mongoConnector) {
        this.memberRepository = new MemberRepository(mongoConnector);
    }

    public boolean isCollectingDataAllowed(final Member member) {
        Objects.requireNonNull(member);
        return memberRepository.findMemberByDiscordId(member.getId())
                .map(m -> m.isCollectingDataAllowed())
                .orElse(false);
    }

    public MemberEntity getOrCreateMemberEntity(final Member member) {
        final MemberEntity memberEntity = memberRepository.findMemberByDiscordId(member.getId()).orElseGet(() -> {
            final MemberEntity entity1 = memberRepository.createNewEntity(member);
            memberRepository.save(entity1);
            return entity1;
        });
        return memberEntity;
    }

    public Optional<MemberEntity> getPersistedMemberEntity(final Member member) {
        Objects.requireNonNull(member);
        return memberRepository.findMemberByDiscordId(member.getId());
    }

    public List<Member> getAllMembers(final JDA jda) {
        final Function<MemberEntity, Member> converter = memberEntity -> {
            return jda.getGuilds().stream()
                    .map(g -> g.getMemberById(memberEntity.getDiscordId()))
                    .filter(m -> m != null)
                    .findAny()
                    .orElse(null);
        };

        final List<Member> allMembersOnDiscord = getAllMembersOnDiscord(jda);
        return memberRepository.findAll().stream()
                .map(entity -> converter.apply(entity))
                .filter(m -> m != null)
                .collect(Collectors.toList());
    }

    private final List<Member> getAllMembersOnDiscord(final JDA jda) {
        return jda.getGuilds().stream().flatMap(g -> g.getMembers().stream()).collect(Collectors.toList());
    }
}
