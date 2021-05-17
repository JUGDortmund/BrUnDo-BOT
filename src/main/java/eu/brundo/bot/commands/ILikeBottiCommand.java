package eu.brundo.bot.commands;

import eu.brundo.bot.data.BadnessManager;
import eu.brundo.bot.util.BottiResourceBundle;
import eu.brundo.bot.util.BrundoUtils;
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
        messages.add(BottiResourceBundle.getMessage("command.ichMagBotti.antwort4"));
        messages.add(BottiResourceBundle.getMessage("command.ichMagBotti.antwort5"));
        messages.add(BottiResourceBundle.getMessage("command.ichMagBotti.antwort6"));


        sendTranslatedMessage(channel, getRandomEntry(messages));
        BadnessManager.getInstance().decrease(BrundoUtils.getUserName(event.getMember()));
    }

    @Override
    public CommandCategories getCategory() {
        return CommandCategories.ADDITIONAL_CATEGORY;
    }
}
