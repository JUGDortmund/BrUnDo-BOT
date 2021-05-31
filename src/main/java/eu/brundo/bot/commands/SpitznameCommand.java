package eu.brundo.bot.commands;

import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SpitznameCommand extends AbstractCommand {

    public SpitznameCommand() {
        super("\u006b\u0069\u006b\u0069");

    }

    @Override
    protected void onCommand(final MessageReceivedEvent event) {
        final MessageChannel channel = event.getChannel();

        final List<String> answers = new ArrayList<>();
        answers.add("\u004d\u0061\u0072\u0065\u0069\u006b\u0069\u006b\u0069");

        final Random random = new Random(System.currentTimeMillis());
        sendTranslatedMessage(channel, getRandomEntry(answers));
    }

    @Override
    public String getHelp() {
        return "Wie heißt Sie wirklich";
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
