package eu.brundo.bot.commands;

import eu.brundo.bot.MongoConnector;
import eu.brundo.bot.achievements.AbstractAchievment;
import eu.brundo.bot.data.TeamManager;
import eu.brundo.bot.services.AchievementService;
import eu.brundo.bot.util.BrundoUtils;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class HighscoreCommand extends AbstractCommand {

    private final AchievementService achievementService;

    private final JDA jda;

    public HighscoreCommand(final JDA jda, final MongoConnector mongoConnector) {
        super("highscore");
        this.jda = jda;
        this.achievementService = new AchievementService(mongoConnector);
    }

    @Override
    protected void onCommand(final MessageReceivedEvent event) {
        final MessageChannel channel = event.getChannel();
        final Set<Member> members = new HashSet<>(achievementService.getAllMembersWithAchievments(jda));
        final int count = Math.min(10, members.size());
        final StringBuilder messageBuilder = new StringBuilder();
        messageBuilder.append(translate("command.highscore.answer", count, TeamManager.getInstance().getRandomAdjective(), TeamManager.getInstance().getRandomAdjective(), TeamManager.getInstance().getRandomRace()));
        members.stream()
                .map(member -> {
                    final List<AbstractAchievment> achievments = achievementService.getAllForMember(member);
                    return new MemberAndAchievments(member, achievments);
                }).sorted()
                .limit(count)
                .forEach(value -> {
                    messageBuilder.append(System.lineSeparator());
                    messageBuilder.append(BrundoUtils.getUserName(value.member) + " -> " + value.getPoints());
                });
        sendTranslatedMessage(channel, messageBuilder.toString());
    }

    @Override
    public CommandCategories getCategory() {
        return CommandCategories.ACHIEVEMTENT_CATEGORY;
    }

    private class MemberAndAchievments implements Comparable<MemberAndAchievments> {

        private final Member member;

        private final List<AbstractAchievment> achievments;

        public MemberAndAchievments(final Member member, final List<AbstractAchievment> achievments) {
            this.member = member;
            this.achievments = new ArrayList<>(achievments);
        }

        public Member getMember() {
            return member;
        }

        public List<AbstractAchievment> getAchievments() {
            return Collections.unmodifiableList(achievments);
        }

        public int getPoints() {
            return getAchievments().stream()
                    .mapToInt(achievment -> achievment.getPoints())
                    .sum();
        }

        @Override
        public int compareTo(@NotNull final HighscoreCommand.MemberAndAchievments o) {
            return Optional.ofNullable(o)
                    .map(value -> value.getPoints())
                    .map(points -> Integer.compare(points, getPoints()))
                    .orElse(1);
        }
    }
}
