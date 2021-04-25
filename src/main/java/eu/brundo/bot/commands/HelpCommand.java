package eu.brundo.bot.commands;

import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.ArrayList;
import java.util.List;

import eu.brundo.bot.AbstractCommand;

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
        textBuilder.append("Hi, ich bin **Botti** 🤖 - der freundliche Brettspiel-BOT von nebenan 😀.")
                .append(System.lineSeparator())
                .append("Sobald du '!botti' in einem Chat eingibst melde ich mich zur Hilfe.")
                .append(System.lineSeparator())
                .append("Ich kann aktuell die folgenden Befehle verstehen:")
                .append(System.lineSeparator());
        commands.stream()
                .filter(command -> command.isAllowed(event.getMember(), event.getChannel()))
                .forEach(command -> {
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
