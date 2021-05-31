package eu.brundo.bot.commands;

import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PennyCommand extends AbstractCommand {

    public PennyCommand() {
        super("ichMagPenny");
    }

    @Override
    protected void onCommand(final MessageReceivedEvent event) {
        final MessageChannel channel = event.getChannel();

        final List<String> answers = new ArrayList<>();
        answers.add("Meinst du Schummel-Penny?? :thinking:");

        final Random random = new Random(System.currentTimeMillis());
        sendTranslatedMessage(channel, getRandomEntry(answers));
    }

    @Override
    public String getHelp() {
        return "Die Wahrheit, nichts als die pure Wahrheit";
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
