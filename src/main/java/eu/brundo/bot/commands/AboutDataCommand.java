package eu.brundo.bot.commands;

import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class AboutDataCommand extends AbstractCommand {

    public AboutDataCommand() {
        super("datensicherheit");
    }

    @Override
    protected void onCommand(final MessageReceivedEvent event) {
        final MessageChannel channel = event.getChannel();
        sendMessage(channel, "command.datensicherheit.antwort");
    }
    
    @Override
    public CommandCategories getCategory() {
        return CommandCategories.ACHIEVEMTENT_CATEGORY;
    }
}
