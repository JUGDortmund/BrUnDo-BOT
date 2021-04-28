package eu.brundo.bot.commands;

import eu.brundo.bot.AbstractCommand;
import eu.brundo.bot.data.Game;
import eu.brundo.bot.util.BottiResourceBundle;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class SuggestGameCommand extends AbstractCommand {

    public SuggestGameCommand() {
        super("spiel");
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
                final Game game = Game.randomForPlayers(playerCount).orElse(null);
                if (game == null) {
                    sendMessage(channel, "command.spiel.fail1", playerCount);
                } else {
                    sendMessage(channel, "command.spiel.antwort", game.getName(), game.getMinPlayerCount(), game.getMaxPlayerCount(), game.getLink());
                }
            } catch (final Exception e) {
                sendMessage(channel, "command.spiel.fail1");
            }
        }
    }

    @Override
    public String getHelp() {
        return BottiResourceBundle.getMessage("command.spiel.help");
    }
}
