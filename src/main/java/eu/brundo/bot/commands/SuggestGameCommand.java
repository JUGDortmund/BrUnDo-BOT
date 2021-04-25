package eu.brundo.bot.commands;

import eu.brundo.bot.AbstractCommand;
import eu.brundo.bot.data.Game;
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
        if(message.length() < "!spiel".length() + 2) {
            channel.sendMessage("Es muss eine Spieleranzahl (als Zahl) angegeben werden. Beispiel: '!spiel 4'").queue();
        } else {
            final String playerCountString = message.substring("!spiel".length() + 1);
            try {
                final int playerCount = Integer.parseInt(playerCountString);
                final Game game = Game.randomForPlayers(playerCount).orElse(null);
                if (game == null) {
                    channel.sendMessage("Leider kenne ich kein Spiel für " + playerCount + " Spieler").queue();
                } else {
                    channel.sendMessage("Wie wär es mit einer Partie **'" + game.getName() + "'**? Das eignet sich super für " + game.getMinPlayerCount() + " bis " + game.getMaxPlayerCount() + " Spieler. Du kannst gleich hier startet: " + game.getLink()).queue();
                }
            } catch (final Exception e) {
                channel.sendMessage("Es muss eine Spieleranzahl (als Zahl) angegeben werden. Beispiel: '!spiel 4'").queue();
            }
        }
    }

    @Override
    public String getHelp() {
        return "Ein Spiel vorschlagen";
    }
}
