package eu.brundo.bot.commands;

import eu.brundo.bot.util.BottiResourceBundle;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.ArrayList;
import java.util.List;

public class ILikeBottiCommand extends AbstractCommand {


    public ILikeBottiCommand() {
        super("ichMagBotti");
    }

    @Override
    protected void onCommand(final MessageReceivedEvent event) {
        final MessageChannel channel = event.getChannel();
        final List<String> messages = new ArrayList<>();
        messages.add(BottiResourceBundle.getMessage("command.ichMagBotti.antwort1"));
        messages.add(BottiResourceBundle.getMessage("command.ichMagBotti.antwort2"));
        messages.add(BottiResourceBundle.getMessage("command.ichMagBotti.antwort3"));
        sendTranslatedMessage(channel, getRandomEntry(messages));
    }

    @Override
    public String getHelp() {
        return BottiResourceBundle.getMessage("command.ichMagBotti.help");
    }

    @Override
    public CommandCategories getCategory() {
        return CommandCategories.ADDITIONAL_CATEGORY;
    }
}
