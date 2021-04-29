package eu.brundo.bot.commands;

import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.Random;

public class CuteDice6Command extends AbstractCommand {

    public CuteDice6Command() {
        super("ww6");
    }

    @Override
    protected void onCommand(final MessageReceivedEvent event) {
        final MessageChannel channel = event.getChannel();
        final Random random = new Random(System.currentTimeMillis());
        final int value = random.nextInt(6) + 1;
        sendMessage(channel, "command.ww6.antwort", value);
    }

    @Override
    public CommandCategories getCategory() {
        return CommandCategories.GAME_CATEGORY;
    }
}
