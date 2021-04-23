package de.brundo.bot.commands;

import de.brundo.bot.AbstractCommand;
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
        final String user = getUserName(event);
        final int value = random.nextInt(6) + 1;
        channel.sendMessage(user + " würfelt eine " + value).queue();
    }

    @Override
    public String getHelp() {
        return "Einen W6 würfeln";
    }
}
