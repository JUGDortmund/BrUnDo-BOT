package eu.brundo.bot.commands;

import eu.brundo.bot.AbstractCommand;
import eu.brundo.bot.util.BottiResourceBundle;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.Random;

public class Dice10Command extends AbstractCommand {

    public Dice10Command() {
        super("w10");
    }

    @Override
    protected void onCommand(final MessageReceivedEvent event) {
        final MessageChannel channel = event.getChannel();
        final Random random = new Random(System.currentTimeMillis());
        final int value = random.nextInt(10) + 1;
        sendMessage(channel, "command.w10.antwort", value);
    }

    @Override
    public String getHelp() {
        return BottiResourceBundle.getMessage("command.w10.help");
    }
}
