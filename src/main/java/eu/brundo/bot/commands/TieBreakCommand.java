package eu.brundo.bot.commands;

import eu.brundo.bot.MongoConnector;
import eu.brundo.bot.achievements.TieLostAchievment;
import eu.brundo.bot.achievements.TieVsBottiAchievment;
import eu.brundo.bot.achievements.TieWonAchievment;
import eu.brundo.bot.data.Game;
import eu.brundo.bot.data.TeamManager;
import eu.brundo.bot.services.AchievementService;
import eu.brundo.bot.util.BrundoUtils;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class TieBreakCommand extends AbstractCommand {

    private final static int MAX_SLEEP_TIME = 5_000;

    private final AchievementService achievementService;

    public TieBreakCommand(final MongoConnector mongoConnector) {
        super("tiebreak");
        this.achievementService = new AchievementService(mongoConnector);
    }

    @Override
    protected void onCommand(final MessageReceivedEvent event) {
        final MessageChannel channel = event.getChannel();
        final List<Member> mentionedMembers = event.getMessage().getMentionedMembers();
        if (mentionedMembers.isEmpty()) {
            sendMessage(channel, "command.tiebreak.fail1");
        } else if (mentionedMembers.size() == 1) {
            sendMessage(channel, "command.tiebreak.fail2");
        } else {
            if (mentionedMembers.size() == 2 && mentionedMembers.contains(event.getMember()) && mentionedMembers.stream().anyMatch(m -> Objects.equals("BrUnDo Botti", m.getNickname()))) {
                if (!achievementService.hasAchived(event.getMember(), new TieVsBottiAchievment())) {
                    achievementService.addAchievement(new TieVsBottiAchievment(), event.getMember());
                }
            }
            final Random random = new Random(System.currentTimeMillis());
            final List<String> working = new ArrayList<>();
            working.add(translate("command.tiebreak.answerA1", TeamManager.getInstance().getRandomRace()));
            working.add(translate("command.tiebreak.answerA2", (random.nextInt(21) + 1)));
            working.add(translate("command.tiebreak.answerA3", (random.nextInt(100) + 1)));
            working.add(translate("command.tiebreak.answerA4"));
            working.add(translate("command.tiebreak.answerA5", TeamManager.getInstance().getRandomAdjective()));
            working.add(translate("command.tiebreak.answerA6", TeamManager.getInstance().getRandomRace()));
            Game.randomForPlayers(mentionedMembers.size()).ifPresent(game -> working.add(translate("command.tiebreak.answerA7", game.getName())));
            Game.randomForPlayers(mentionedMembers.size()).ifPresent(game -> working.add(translate("command.tiebreak.answerA8", game.getName())));
            if (mentionedMembers.size() == 2) {
                working.add(translate("command.tiebreak.answerA9", BrundoUtils.getUserName(mentionedMembers.get(0)), BrundoUtils.getUserName(mentionedMembers.get(1))));
                working.add(translate("command.tiebreak.answerA10"));
                working.add(translate("command.tiebreak.answerA11", BrundoUtils.getUserName(mentionedMembers.get(0)), BrundoUtils.getUserName(mentionedMembers.get(1))));
            }
            working.add(translate("command.tiebreak.answerA12", mentionedMembers.size()));
            working.add(translate("command.tiebreak.answerA13", TeamManager.getInstance().getRandomAdjective()));
            working.add(translate("command.tiebreak.answerA14", mentionedMembers.size(), TeamManager.getInstance().getRandomAdjective()));
            working.add(translate("command.tiebreak.answerA15"));

            final List<String> sleeping = new ArrayList<>();
            sleeping.add(translate("command.tiebreak.answerB1"));
            sleeping.add(translate("command.tiebreak.answerB2"));
            sleeping.add(translate("command.tiebreak.answerB3"));
            sendTranslatedMessage(channel, getRandomEntry(sleeping));

            sendTyping(channel);
            sleep(random.nextInt(MAX_SLEEP_TIME));
            sendTranslatedMessage(channel, getAndRemoveRandomEntry(working));
            sendTyping(channel);
            int counter = 0;
            while (random.nextBoolean() && counter < 4) {
                sleep(random.nextInt(MAX_SLEEP_TIME));
                final List<String> again = new ArrayList<>();
                again.add(translate("command.tiebreak.answerC1"));
                again.add(translate("command.tiebreak.answerC2"));
                again.add(translate("command.tiebreak.answerC3", TeamManager.getInstance().getRandomAdjective(), TeamManager.getInstance().getRandomRace()));
                again.add(translate("command.tiebreak.answerC4"));
                again.add(translate("command.tiebreak.answerC5"));
                sendTranslatedMessage(channel, getRandomEntry(again));
                sendTyping(channel);
                sleep(random.nextInt(MAX_SLEEP_TIME));
                sendTranslatedMessage(channel, getAndRemoveRandomEntry(working));
                sendTyping(channel);
                counter++;
            }
            sleep(random.nextInt(MAX_SLEEP_TIME));
            final Member winner = mentionedMembers.get(random.nextInt(mentionedMembers.size()));
            final List<String> finalWinner = new ArrayList<>();
            finalWinner.add(translate("command.tiebreak.answerD1", BrundoUtils.getUserName(winner)));
            finalWinner.add(translate("command.tiebreak.answerD2", BrundoUtils.getUserName(winner)));
            finalWinner.add(translate("command.tiebreak.answerD3", BrundoUtils.getUserName(winner)));
            finalWinner.add(translate("command.tiebreak.answerD4", BrundoUtils.getUserName(winner)));
            finalWinner.add(translate("command.tiebreak.answerD5", BrundoUtils.getUserName(winner)));
            sendTranslatedMessage(channel, getRandomEntry(finalWinner));

            if (!achievementService.hasAchived(winner, new TieWonAchievment())) {
                achievementService.addAchievement(new TieWonAchievment(), winner);
            }
            if (mentionedMembers.size() == 2) {
                mentionedMembers.stream()
                        .filter(member -> !Objects.equals(winner, member))
                        .findAny()
                        .ifPresent(looser -> {
                            if (!achievementService.hasAchived(looser, new TieLostAchievment())) {
                                achievementService.addAchievement(new TieLostAchievment(), looser);
                            }
                        });
            }
        }
    }

    @Override
    public CommandCategories getCategory() {
        return CommandCategories.GAME_CATEGORY;
    }
}
