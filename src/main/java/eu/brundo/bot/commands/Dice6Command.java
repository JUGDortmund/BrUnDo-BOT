package eu.brundo.bot.commands;

import eu.brundo.bot.util.BottiResourceBundle;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.Random;

public class Dice6Command extends AbstractCommand {

    public Dice6Command() {
        super("w6");
    }

    @Override
    protected void onCommand(final MessageReceivedEvent event) {
        final MessageChannel channel = event.getChannel();
        final Random random = new Random(System.currentTimeMillis());
        final int value = random.nextInt(6) + 1;
        sendMessage(channel, "command.w6.antwort", value);
    }

    @Override
    public String getHelp() {
        return BottiResourceBundle.getMessage("command.w6.help");
    }

    @Override
    public CommandCategories getCategory() {
        return CommandCategories.GAME_CATEGORY;
    }
}
