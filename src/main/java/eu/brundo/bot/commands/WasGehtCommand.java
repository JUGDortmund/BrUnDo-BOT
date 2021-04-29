package eu.brundo.bot.commands;

import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class WasGehtCommand extends AbstractCommand {

    public WasGehtCommand() {
        super("wasGeht");
    }

    @Override
    protected void onCommand(final MessageReceivedEvent event) {
        final MessageChannel channel = event.getChannel();
        sendTranslatedMessage(channel, "Na heute und morgen gehts ab!");
    }

    @Override
    public String getHelp() {
        return "Der Command zum 1. Mai";
    }

    @Override
    public boolean listInHelp() {
        return false;
    }

    @Override
    public CommandCategories getCategory() {
        return CommandCategories.ADDITIONAL_CATEGORY;
    }
}
