package eu.brundo.bot.commands;

import eu.brundo.bot.data.BadnessManager;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.Optional;

public class DecreaseBadnessCommand extends AbstractCommand {

    public DecreaseBadnessCommand() {
        super("liebe");
    }

    @Override
    protected void onCommand(final MessageReceivedEvent event) {
        final String name = event.getMessage().getContentDisplay().substring("liebe ".length()).trim().toLowerCase();
        final Optional<String> userThatContains = BadnessManager.getInstance().findUserThatContains(name);
        if (userThatContains.isPresent()) {
            final String userName = userThatContains.get();
            BadnessManager.getInstance().decrease(userName);
            BadnessManager.getInstance().decrease(userName);
            sendTranslatedMessage(event.getChannel(), "CHECK " + BadnessManager.getInstance().getBadness(userName));
        } else {
            sendTranslatedMessage(event.getChannel(), "NOT FOUND");
        }
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
