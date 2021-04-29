package eu.brundo.bot.commands;

import eu.brundo.bot.data.KapernDice;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class KapernCommand extends AbstractCommand {

    public KapernCommand() {
        super("kapern");
    }

    @Override
    protected void onCommand(final MessageReceivedEvent event) {
        final MessageChannel channel = event.getChannel();
        final StringBuilder textBuilder = new StringBuilder();
        sendMessage(channel, "command.kapern.antwort", KapernDice.random().getDefinition(), KapernDice.random().getDefinition(), KapernDice.random().getDefinition(), KapernDice.random().getDefinition(), KapernDice.random().getDefinition());
    }

    @Override
    public CommandCategories getCategory() {
        return CommandCategories.GAME_CATEGORY;
    }
}
