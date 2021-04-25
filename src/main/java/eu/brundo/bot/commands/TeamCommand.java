package eu.brundo.bot.commands;

import eu.brundo.bot.AbstractCommand;
import eu.brundo.bot.data.TeamManager;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class TeamCommand extends AbstractCommand {

    public TeamCommand() {
        super("tiername");
    }

    @Override
    protected void onCommand(final MessageReceivedEvent event) {
        final MessageChannel channel = event.getChannel();
        channel.sendMessage("Hier ein cooler Name für dein Team: **" + TeamManager.getInstance().getRandomTeamName() + "**").queue();
    }

    @Override
    public String getHelp() {
        return "Gibt den Namen eines zufälligen Teams aus";
    }
}
