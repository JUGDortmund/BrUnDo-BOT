package eu.brundo.bot.commands;

import eu.brundo.bot.AbstractCommand;
import eu.brundo.bot.util.BottiResourceBundle;
import eu.brundo.bot.util.BrundoUtils;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.ArrayList;
import java.util.List;

public class CanIGoToBedCommand extends AbstractCommand {

    public CanIGoToBedCommand() {
        super("darfIchInsBett");
    }

    @Override
    protected void onCommand(final MessageReceivedEvent event) {
        final MessageChannel channel = event.getChannel();
        final List<String> messages = new ArrayList<>();
        messages.add(BottiResourceBundle.getMessage("command.darfIchInsBett.antwort1"));
        messages.add(BottiResourceBundle.getMessage("command.darfIchInsBett.antwort2"));
        messages.add(BottiResourceBundle.getMessage("command.darfIchInsBett.antwort3"));
        messages.add(BottiResourceBundle.getMessage("command.darfIchInsBett.antwort4"));

        final List<Member> members = BrundoUtils.getAllMembersInVoiceChannels(event.getJDA());
        if (!members.isEmpty()) {
            final String name = getUserName(getRandomEntry(members));
            messages.add(BottiResourceBundle.getMessage("command.darfIchInsBett.antwort5", name));
            messages.add(BottiResourceBundle.getMessage("command.darfIchInsBett.antwort6", name));
        } else {
            messages.add(BottiResourceBundle.getMessage("command.darfIchInsBett.antwort7"));
        }
        channel.sendMessage(getRandomEntry(messages)).queue();
    }

    @Override
    public String getHelp() {
        return BottiResourceBundle.getMessage("command.darfIchInsBett.help");
    }
}
