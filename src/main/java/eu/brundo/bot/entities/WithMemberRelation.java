package eu.brundo.bot.entities;

public interface WithMemberRelation extends IdBasedEntity {

    MemberEntity getMember();

    void setMember(final MemberEntity member);
}
