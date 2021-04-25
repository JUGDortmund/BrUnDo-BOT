package eu.brundo.bot.commands;

import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.Random;

import eu.brundo.bot.AbstractCommand;

public class CuteDice6Command extends AbstractCommand {

    public CuteDice6Command() {
        super("ww6");
    }

    @Override
    protected void onCommand(final MessageReceivedEvent event) {
        final MessageChannel channel = event.getChannel();
        final Random random = new Random(System.currentTimeMillis());
        final int value = random.nextInt(6) + 1;
        channel.sendMessage("Würfel, würfel, würfel " + value).queue();
    }

    @Override
    public String getHelp() {
        return "Einen W6 würfeln";
    }
}
