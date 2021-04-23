package de.brundo.bot.commands;

import de.brundo.bot.AbstractCommand;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.Random;

public class Dice10Command extends AbstractCommand {

    public Dice10Command() {
        super("w1");
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
        return "Einen W1 würfeln";
    }
}
