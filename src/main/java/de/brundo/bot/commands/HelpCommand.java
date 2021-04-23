package de.brundo.bot.commands;

import de.brundo.bot.AbstractCommand;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.ArrayList;
import java.util.List;

public class HelpCommand extends AbstractCommand {

    final List<AbstractCommand> commands;

    public HelpCommand(final List<AbstractCommand> commands) {
        super("botti");
        this.commands = new ArrayList<>(commands);
        this.commands.add(this);
    }

    @Override
    protected void onCommand(final MessageReceivedEvent event) {
        final StringBuilder textBuilder = new StringBuilder();
        textBuilder.append("Folgende Commands stehen zur Verfügung:");
        textBuilder.append(System.lineSeparator());
        commands.forEach(command -> {
            textBuilder.append("**" + AbstractCommand.EXCLAMATION_MARK + command.getCommand() + "** -> " + command.getHelp());
            textBuilder.append(System.lineSeparator());
        });
        final MessageChannel channel = event.getChannel();
        channel.sendMessage(textBuilder.toString()).queue();
    }

    @Override
    public String getHelp() {
        return "Zeigt diese Liste an";
    }
}
