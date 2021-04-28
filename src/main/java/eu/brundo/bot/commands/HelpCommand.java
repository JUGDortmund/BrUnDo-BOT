package eu.brundo.bot.commands;

import eu.brundo.bot.AbstractCommand;
import eu.brundo.bot.util.BottiResourceBundle;
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
        final MessageChannel channel = event.getChannel();
        sendMessage(channel, "command.botti.antwort");

        final StringBuilder textBuilder = new StringBuilder();
        commands.stream()
                .filter(command -> command.isAllowed(event.getMember(), event.getChannel()))
                .forEach(command -> {
                    textBuilder.append("**" + AbstractCommand.EXCLAMATION_MARK + command.getCommand() + "** -> " + command.getHelp());
                    textBuilder.append(System.lineSeparator());
                });
        channel.sendMessage(textBuilder.toString()).queue();
    }

    @Override
    public String getHelp() {
        return BottiResourceBundle.getMessage("command.botti.help");
    }
}
