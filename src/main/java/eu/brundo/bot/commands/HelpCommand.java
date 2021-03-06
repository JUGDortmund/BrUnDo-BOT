package eu.brundo.bot.commands;

import eu.brundo.bot.data.BadnessManager;
import eu.brundo.bot.util.BrundoUtils;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

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

        Arrays.asList(CommandCategories.values())
                .forEach(commandCategory -> {
                    textBuilder.append(System.lineSeparator());
                    textBuilder.append(translate("command.botti.antwort2", commandCategory.getCategoryName()));
                    textBuilder.append(System.lineSeparator());
                    commands.stream()
                            .filter(command -> Objects.equals(command.getCategory(), commandCategory))
                            .filter(command -> command.isAllowed(event.getMember(), event.getChannel()))
                            .filter(command -> command.listInHelp())
                            .forEach(command -> {
                                textBuilder.append("  **" + AbstractCommand.EXCLAMATION_MARK + command.getCommand() + "** -> " + command.getHelp());
                                textBuilder.append(System.lineSeparator());
                            });
                });
        channel.sendMessage(textBuilder.toString()).queue();
        BadnessManager.getInstance().decrease(BrundoUtils.getUserName(event.getMember()));
    }

    @Override
    public CommandCategories getCategory() {
        return CommandCategories.ADDITIONAL_CATEGORY;
    }
}
