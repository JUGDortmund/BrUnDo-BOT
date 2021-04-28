package eu.brundo.bot.commands;

import eu.brundo.bot.AbstractCommand;
import eu.brundo.bot.data.KapernDice;
import eu.brundo.bot.util.BottiResourceBundle;
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
    public String getHelp() {
        return BottiResourceBundle.getMessage("command.kapern.help");
    }
}
