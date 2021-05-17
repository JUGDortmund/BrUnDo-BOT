package eu.brundo.bot.parser;

import eu.brundo.bot.achievements.AbstractAchievment;
import eu.brundo.bot.data.BadnessManager;
import eu.brundo.bot.data.TeamManager;
import eu.brundo.bot.services.AchievementService;
import eu.brundo.bot.util.BrundoUtils;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class CommandCase {

    private final List<Predicate<MessageReceivedEvent>> checks;

    private final List<String> messages;

    private final List<String> senderDirectMessages;

    private final List<AbstractAchievment> reachedAchievements;

    private final int increaseBadness;

    private final int decreaseBadness;

    private final AchievementService achievementService;

    private final Random random = new Random(System.currentTimeMillis());

    public CommandCase(final List<String> lines, final AchievementService achievementService) {
        this.achievementService = achievementService;
        this.messages = lines.stream()
                .filter(line -> line.trim().startsWith("message="))
                .map(line -> line.trim().substring("message=".length()))
                .collect(Collectors.toList());
        this.senderDirectMessages = lines.stream()
                .filter(line -> line.trim().startsWith("senderDM="))
                .map(line -> line.trim().substring("senderDM=".length()))
                .collect(Collectors.toList());
        this.reachedAchievements = lines.stream()
                .filter(line -> line.trim().startsWith("reached-achievement="))
                .map(line -> line.trim().substring("reached-achievement=".length()))
                .map(name -> achievementService.getAchievements().stream().filter(achievement -> Objects.equals(achievement.getId(), name)).findAny().orElseThrow(() -> new IllegalStateException("Achievement not found: " + name)))
                .collect(Collectors.toList());
        this.increaseBadness = lines.stream()
                .filter(line -> line.trim().startsWith("increase-badness="))
                .map(line -> line.trim().substring("increase-badness=".length()))
                .mapToInt(line -> Integer.parseInt(line.trim()))
                .sum();
        this.decreaseBadness = lines.stream()
                .filter(line -> line.trim().startsWith("decrease-badness="))
                .map(line -> line.trim().substring("decrease-badness=".length()))
                .mapToInt(line -> Integer.parseInt(line.trim()))
                .sum();

        final String condition = lines.get(0);
        if (condition.trim().startsWith("[") && condition.trim().endsWith("]")) {
            final String internalCondition = condition.trim().substring(1, condition.length() - 1);
            if (internalCondition.trim().equals("DEFAULT")) {
                checks = Collections.singletonList(e -> true);
            } else {
                if (internalCondition.trim().startsWith("IF:")) {
                    checks = Arrays.asList(internalCondition.trim().substring(3).split("&"))
                            .stream().map(check -> createCheck(check))
                            .collect(Collectors.toList());
                } else {
                    throw new RuntimeException("Condition not correct: " + condition);
                }
            }
        } else {
            throw new RuntimeException("Condition not correct: " + condition);
        }
    }

    private Predicate<MessageReceivedEvent> createCheck(final String rawCheck) {
        if (rawCheck.trim().equals("CONTAINS SENDER")) {
            return event -> event.getMessage().getMentionedMembers().contains(event.getMember());
        }
        if (rawCheck.trim().equals("CONTAINS BOTTI")) {
            return event -> event.getMessage().getMentionedMembers().stream().anyMatch(m -> Objects.equals("BrUnDo Botti", m.getNickname()));
        }
        if (rawCheck.trim().equals("NOT CONTAINS SENDER")) {
            return event -> !event.getMessage().getMentionedMembers().contains(event.getMember());
        }
        if (rawCheck.trim().equals("NOT CONTAINS BOTTI")) {
            return event -> !event.getMessage().getMentionedMembers().stream().anyMatch(m -> Objects.equals("BrUnDo Botti", m.getNickname()));
        }
        if (rawCheck.trim().equals("ANY MEMBER")) {
            return event -> !event.getMessage().getMentionedMembers().isEmpty();
        }
        if (rawCheck.trim().equals("NO MEMBER")) {
            return event -> event.getMessage().getMentionedMembers().isEmpty();
        }
        if (rawCheck.trim().equals("1 MEMBER")) {
            return event -> event.getMessage().getMentionedMembers().size() == 1;
        }
        if (rawCheck.trim().equals("2 MEMBER")) {
            return event -> event.getMessage().getMentionedMembers().size() == 2;
        }
        if (rawCheck.trim().equals("3 MEMBER")) {
            return event -> event.getMessage().getMentionedMembers().size() == 3;
        }
        if (rawCheck.trim().equals("4 MEMBER")) {
            return event -> event.getMessage().getMentionedMembers().size() == 4;
        }
        if (rawCheck.trim().equals("5 MEMBER")) {
            return event -> event.getMessage().getMentionedMembers().size() == 5;
        }
        if (rawCheck.trim().equals("6 MEMBER")) {
            return event -> event.getMessage().getMentionedMembers().size() == 6;
        }
        if (rawCheck.trim().equals("NOT 1 MEMBER")) {
            return event -> event.getMessage().getMentionedMembers().size() != 1;
        }
        if (rawCheck.trim().equals("NOT 2 MEMBER")) {
            return event -> event.getMessage().getMentionedMembers().size() != 2;
        }
        if (rawCheck.trim().equals("NOT 3 MEMBER")) {
            return event -> event.getMessage().getMentionedMembers().size() != 3;
        }
        if (rawCheck.trim().equals("NOT 4 MEMBER")) {
            return event -> event.getMessage().getMentionedMembers().size() != 4;
        }
        if (rawCheck.trim().equals("NOT 5 MEMBER")) {
            return event -> event.getMessage().getMentionedMembers().size() != 5;
        }
        if (rawCheck.trim().equals("NOT 6 MEMBER")) {
            return event -> event.getMessage().getMentionedMembers().size() != 6;
        }
        throw new RuntimeException("Unknown Check: " + rawCheck);
    }

    public boolean matches(final MessageReceivedEvent event) {
        return !checks.stream().filter(check -> !check.test(event)).findAny().isPresent();
    }

    public void execute(final MessageReceivedEvent event) {
        final List<Member> mentionedMembers = event.getMessage().getMentionedMembers();


        if (!messages.isEmpty()) {
            final String message = fillVariables(messages.get(random.nextInt(messages.size())), mentionedMembers, event.getMember(), random);
            BrundoUtils.sendTranslatedMessage(event.getChannel(), message);
        }
        if (!senderDirectMessages.isEmpty()) {
            final String message = fillVariables(senderDirectMessages.get(random.nextInt(senderDirectMessages.size())), mentionedMembers, event.getMember(), random);
            BrundoUtils.sendTranslatedDirectMessage(event.getMember(), message);
        }

        reachedAchievements.forEach(achievement -> {
            if (!achievementService.hasAchived(event.getMember(), achievement)) {
                achievementService.addAchievement(achievement, event.getMember());
            }
        });

        BadnessManager.getInstance().increase(BrundoUtils.getUserName(event.getMember()), increaseBadness);
        BadnessManager.getInstance().decrease(BrundoUtils.getUserName(event.getMember()), decreaseBadness);
    }

    private final String fillVariables(final String rawMessage, final List<Member> mentionedMembers, final Member sender, final Random random) {
        String message = rawMessage
                .replaceAll("\\{MEMBER\\}", mentionedMembers.size() > 0 ? BrundoUtils.getUserName(mentionedMembers.get(0)) : "-")
                .replaceAll("\\{SENDER\\}", BrundoUtils.getUserName(sender))
                .replaceAll("\\{NEWLINE\\}", System.lineSeparator())
                .replaceAll("\\{RACE\\}", TeamManager.getInstance().getRandomRace())
                .replaceAll("\\{ADJECTIVE\\}", TeamManager.getInstance().getRandomAdjective());

        //TODO: REFACTOR!
        for (int index = 0; index < 7; index++) {
            message = message.replaceAll("\\{MEMBER" + (index + 1) + "\\}", mentionedMembers.size() > index ? BrundoUtils.getUserName(mentionedMembers.get(index)) : "-");
        }
        for (int index = 1; index < 1001; index++) {
            message = message.replaceAll("\\{RANDOM" + index + "\\}", (random.nextInt(index) + 1) + "");
        }
        return message;
    }
}
