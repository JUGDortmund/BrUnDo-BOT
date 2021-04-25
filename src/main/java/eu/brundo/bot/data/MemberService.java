package eu.brundo.bot.data;

import eu.brundo.bot.MongoConnector;
import eu.brundo.bot.repositories.MemberRepository;
import eu.brundo.bot.store.MemberEntity;
import net.dv8tion.jda.api.entities.Member;

public class MemberService {

    private final MemberRepository memberRepository;

    public MemberService(final MongoConnector mongoConnector) {
        this.memberRepository = new MemberRepository(mongoConnector);
    }

    public boolean isCollectingDataAllowed(final Member member) {
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
}
