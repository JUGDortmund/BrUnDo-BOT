package eu.brundo.bot.commands;

import eu.brundo.bot.util.BrundoUtils;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.Random;

public class CustomDice6Command extends AbstractCommand {

    public CustomDice6Command() {
        super("wme6");
    }

    @Override
    protected void onCommand(final MessageReceivedEvent event) {
        final MessageChannel channel = event.getChannel();
        final Random random = new Random(System.currentTimeMillis());
        final String user = BrundoUtils.getUserName(event);
        final int value = random.nextInt(6) + 1;
        sendMessage(channel, "command.wme6.antwort", user, value);
    }

    @Override
    public CommandCategories getCategory() {
        return CommandCategories.GAME_CATEGORY;
    }
}
