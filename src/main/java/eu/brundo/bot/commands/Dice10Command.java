package eu.brundo.bot.commands;

import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.Random;

import eu.brundo.bot.AbstractCommand;

public class Dice10Command extends AbstractCommand {

    public Dice10Command() {
        super("w10");
    }

    @Override
    protected void onCommand(final MessageReceivedEvent event) {
        final MessageChannel channel = event.getChannel();
        final Random random = new Random(System.currentTimeMillis());
        final int value = random.nextInt(10) + 1;
        channel.sendMessage(":game_die: " + value).queue();
    }

    @Override
    public String getHelp() {
        return "Einen W10 würfeln";
    }
}
