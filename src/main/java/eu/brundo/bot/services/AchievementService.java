package eu.brundo.bot.services;

import eu.brundo.bot.MongoConnector;
import eu.brundo.bot.achievements.AbstractAchievment;
import eu.brundo.bot.achievements.AbstractCheckableAchievement;
import eu.brundo.bot.achievements.AloneInTreffpunktAchievement;
import eu.brundo.bot.achievements.DerKoljaAchievment;
import eu.brundo.bot.achievements.FirstTimeInTreffpunktAchievement;
import eu.brundo.bot.achievements.IntroducedAchievement;
import eu.brundo.bot.achievements.MidnightPlayerAchievement;
import eu.brundo.bot.achievements.MonopolyAchievment;
import eu.brundo.bot.achievements.NegativerBottiAchievment;
import eu.brundo.bot.achievements.NightWatchAchievement;
import eu.brundo.bot.achievements.PackPlayerAchievement;
import eu.brundo.bot.achievements.PlayedAtAllTablesAchievement;
import eu.brundo.bot.achievements.SitzfleischAchievement;
import eu.brundo.bot.achievements.SpalterAchievment;
import eu.brundo.bot.achievements.TieLostAchievment;
import eu.brundo.bot.achievements.TieVsBottiAchievment;
import eu.brundo.bot.achievements.TieWonAchievment;
import eu.brundo.bot.entities.AchievementEntity;
import eu.brundo.bot.entities.MemberEntity;
import eu.brundo.bot.repositories.AchievementRepository;
import eu.brundo.bot.util.BottiResourceBundle;
import eu.brundo.bot.util.BrundoUtils;
import eu.brundo.bot.util.TimeUtils;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Member;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

public class AchievementService {

    private final static Logger LOG = LoggerFactory.getLogger(AchievementService.class);

    private final MemberService memberService;

    private final AchievementRepository achievementRepository;

    private final List<AbstractAchievment> achievements;

    public AchievementService(final MongoConnector mongoConnector) {
        this.memberService = new MemberService(mongoConnector);
        this.achievementRepository = new AchievementRepository(mongoConnector);
        achievements = new ArrayList<>();
        achievements.add(new AloneInTreffpunktAchievement(mongoConnector));
        achievements.add(new FirstTimeInTreffpunktAchievement(mongoConnector));
        achievements.add(new IntroducedAchievement(mongoConnector));
        achievements.add(new MonopolyAchievment());
        achievements.add(new PackPlayerAchievement(mongoConnector));
        achievements.add(new PlayedAtAllTablesAchievement(mongoConnector));
        achievements.add(new SitzfleischAchievement(mongoConnector));
        achievements.add(new TieLostAchievment());
        achievements.add(new TieWonAchievment());
        achievements.add(new NightWatchAchievement(mongoConnector));
        achievements.add(new SpalterAchievment());
        achievements.add(new MidnightPlayerAchievement(mongoConnector));
        achievements.add(new DerKoljaAchievment());
        achievements.add(new NegativerBottiAchievment());
        achievements.add(new TieVsBottiAchievment());
    }

    public void checkAll(final List<Member> knownMembers) {
        knownMembers.forEach(member -> {
            if (memberService.isCollectingDataAllowed(member)) {
                final MemberEntity memberEntity = memberService.getOrCreateMemberEntity(member);
                achievements.stream()
                        .filter(achievement -> achievement instanceof AbstractCheckableAchievement)
                        .map(achievement -> (AbstractCheckableAchievement) achievement)
                        .forEach(achievement -> {
                            LOG.debug("Checking Achievement: User '{}' Erfolg '{}'", member.getEffectiveName(), achievement.getName());
                            if (!achievementRepository.hasAchived(memberEntity, achievement.getId()) && achievement.achived(member)) {
                                addAchievement(achievement, member);
                            }
                        });
            }
        });
    }

    public List<AbstractAchievment> getAchievements() {
        return Collections.unmodifiableList(achievements);
    }

    public boolean hasAchived(final Member member, final AbstractAchievment achievement) {
        Objects.requireNonNull(member);
        Objects.requireNonNull(achievement);
        return memberService.getPersistedMemberEntity(member)
                .map(memberEntity -> achievementRepository.hasAchived(memberEntity, achievement.getId()))
                .orElse(false);
    }

    public void addAchievement(final AbstractAchievment achievement, final Member member) {
        Objects.requireNonNull(member);
        Objects.requireNonNull(achievement);
        if (memberService.isCollectingDataAllowed(member)) {
            final MemberEntity memberEntity = memberService.getOrCreateMemberEntity(member);
            final AchievementEntity achievementEntity = achievementRepository.createEntity();
            achievementEntity.setAchievementId(achievement.getId());
            achievementEntity.setMember(memberEntity);
            achievementEntity.setAchived(TimeUtils.convertToDate(TimeUtils.nowInGermany()));
            achievementRepository.save(achievementEntity);
            LOG.info("User '{}' hat Erfolg '{}' freigeschaltet!", member.getEffectiveName(), achievement.getName());

            final String message = new StringBuilder().append(BottiResourceBundle.getMessage("dm.achievements.received"))
                    .append(System.lineSeparator())
                    .append("**" + achievement.getName() + "** -> " + achievement.getDescription())
                    .toString();
            BrundoUtils.sendTranslatedDirectMessage(member, message);
        }
    }

    public List<AbstractAchievment> getAllForMember(final Member member) {
        Objects.requireNonNull(member);
        if (memberService.isCollectingDataAllowed(member)) {
            final MemberEntity memberEntity = memberService.getOrCreateMemberEntity(member);
            return achievementRepository.getAllForMember(memberEntity).stream()
                    .map(achievement -> getAchievementById(achievement.getAchievementId()).orElse(null))
                    .filter(achievement -> achievement != null)
                    .collect(Collectors.toList());
        } else {
            return Collections.emptyList();
        }
    }

    private Optional<AbstractAchievment> getAchievementById(final String id) {
        return achievements.stream()
                .filter(achievement -> Objects.equals(achievement.getId(), id))
                .findAny();
    }

    public List<Member> getAllMembersWithAchievments(final JDA jda) {
        return achievementRepository.getAll().stream()
                .map(achievement -> achievement.getMember())
                .map(memberEntity -> findMemberById(jda, memberEntity.getDiscordId()).orElse(null))
                .filter(member -> member != null)
                .collect(Collectors.toList());
    }

    private Optional<Member> findMemberById(final JDA jda, final String id) {
        return jda.getGuilds().stream()
                .map(guild -> guild.retrieveMemberById(id).complete())
                .filter(member -> member != null)
                .findAny();
    }
}
