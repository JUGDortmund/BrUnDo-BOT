package eu.brundo.bot.services;

import eu.brundo.bot.MongoConnector;
import eu.brundo.bot.achievements.AbstractAchievement;
import eu.brundo.bot.achievements.FirstTimeInTreffpunktAchievement;
import eu.brundo.bot.achievements.PlayedAtAllTablesAchievement;
import eu.brundo.bot.entities.AchievementEntity;
import eu.brundo.bot.entities.MemberEntity;
import eu.brundo.bot.repositories.AchievementRepository;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.PrivateChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

public class AchievementService {

    private final static Logger LOG = LoggerFactory.getLogger(AchievementService.class);

    private final MemberService memberService;

    private final AchievementRepository achievementRepository;

    private final List<AbstractAchievement> achievements;

    public AchievementService(final MongoConnector mongoConnector) {
        this.memberService = new MemberService(mongoConnector);
        this.achievementRepository = new AchievementRepository(mongoConnector);
        achievements = new ArrayList<>();
        achievements.add(new FirstTimeInTreffpunktAchievement(mongoConnector));
        achievements.add(new PlayedAtAllTablesAchievement(mongoConnector));
    }

    public void checkAll(final List<Member> knownMembers) {
        knownMembers.forEach(member -> {
            if (memberService.isCollectingDataAllowed(member)) {
                final MemberEntity memberEntity = memberService.getOrCreateMemberEntity(member);
                achievements.forEach(achievement -> {
                    LOG.info("Checking Achievement: User '{}' Erfolg '{}'", member.getEffectiveName(), achievement.getName());
                    if (!achievementRepository.hasAchived(memberEntity, achievement.getId()) && achievement.achived(member)) {
                        addAchievement(achievement, member);
                    }
                });
            }
        });
    }

    public List<AbstractAchievement> getAchievements() {
        return Collections.unmodifiableList(achievements);
    }

    public boolean hasAchived(final Member member, final AbstractAchievement achievement) {
        Objects.requireNonNull(member);
        Objects.requireNonNull(achievement);
        return memberService.getPersistedMemberEntity(member)
                .map(memberEntity -> achievementRepository.hasAchived(memberEntity, achievement.getId()))
                .orElse(false);
    }

    public void addAchievement(final AbstractAchievement achievement, final Member member) {
        Objects.requireNonNull(member);
        Objects.requireNonNull(achievement);
        if (memberService.isCollectingDataAllowed(member)) {
            final MemberEntity memberEntity = memberService.getOrCreateMemberEntity(member);
            final AchievementEntity achievementEntity = achievementRepository.createEntity();
            achievementEntity.setAchievementId(achievement.getId());
            achievementEntity.setMember(memberEntity);
            achievementEntity.setAchived(Date.from(ZonedDateTime.now(ZoneId.of("Europe/Berlin")).toInstant()));
            achievementRepository.save(achievementEntity);
            LOG.info("User '{}' hat Erfolg '{}' freigeschaltet!", member.getEffectiveName(), achievement.getName());

            final PrivateChannel privateChannel = member.getUser().openPrivateChannel().complete();
            privateChannel.sendMessage("Herzlichen Glückwunsch, du hast ein Erfolg freigeschaltet :trophy: :trophy: :trophy:").complete();
            privateChannel.sendMessage("**" + achievement.getName() + "** -> " + achievement.getDescription()).complete();
        }
    }

    public List<AbstractAchievement> getAllForMember(final Member member) {
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

    private Optional<AbstractAchievement> getAchievementById(final String id) {
        return achievements.stream()
                .filter(achievement -> Objects.equals(achievement.getId(), id))
                .findAny();
    }
}