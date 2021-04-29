package eu.brundo.bot.commands;

import eu.brundo.bot.MongoConnector;
import eu.brundo.bot.achievements.NegativerBottiAchievment;
import eu.brundo.bot.data.Game;
import eu.brundo.bot.services.AchievementService;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class SuggestGameCommand extends AbstractCommand {

    private final AchievementService achievementService;

    public SuggestGameCommand(final MongoConnector mongoConnector) {
        super("spiel");
        this.achievementService = new AchievementService(mongoConnector);
    }

    @Override
    protected void onCommand(final MessageReceivedEvent event) {
        final MessageChannel channel = event.getChannel();

        final String message = event.getMessage().getContentDisplay();
        if (message.length() < "!spiel".length() + 2) {
            sendMessage(channel, "command.spiel.fail1");
        } else {
            final String playerCountString = message.substring("!spiel".length() + 1);
            try {
                final int playerCount = Integer.parseInt(playerCountString);
                if (playerCount == 1) {
                    sendMessage(channel, "command.spiel.answer1");
                }
                if (playerCount == 0) {
                    sendMessage(channel, "command.spiel.answer2");
                }
                if (playerCount < 0) {
                    sendMessage(channel, "command.spiel.answer3");
                    final NegativerBottiAchievment achievement = new NegativerBottiAchievment();
                    if (!achievementService.hasAchived(event.getMember(), achievement)) {
                        achievementService.addAchievement(achievement, event.getMember());
                    }
                }
                final Game game = Game.randomForPlayers(playerCount).orElse(null);
                if (game == null) {
                    sendMessage(channel, "command.spiel.fail1", playerCount);
                } else {
                    sendMessage(channel, "command.spiel.answer4", game.getName(), game.getMinPlayerCount(), game.getMaxPlayerCount(), game.getLink());
                }
            } catch (final Exception e) {
                sendMessage(channel, "command.spiel.fail1");
            }
        }
    }

    @Override
    public CommandCategories getCategory() {
        return CommandCategories.GAME_CATEGORY;
    }
}
