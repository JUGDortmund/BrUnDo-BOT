package eu.brundo.bot.commands;

import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.stream.IntStream;

import eu.brundo.bot.AbstractCommand;
import eu.brundo.bot.data.KapernDice;

public class KapernCommand extends AbstractCommand {

    public KapernCommand() {
        super("kapern");
    }

    @Override
    protected void onCommand(final MessageReceivedEvent event) {
        final MessageChannel channel = event.getChannel();
        final StringBuilder textBuilder = new StringBuilder();
        textBuilder.append("Auf geht die Kaperfahrt:");
        IntStream.range(0, 5).forEach(i -> textBuilder.append(" " + KapernDice.random().getDefinition()));
        channel.sendMessage(textBuilder.toString()).queue();
    }

    @Override
    public String getHelp() {
        return "Einen Kaperfahrt würfeln";
    }
}
