package eu.brundo.bot.commands;

import eu.brundo.bot.AbstractCommand;
import eu.brundo.bot.data.BrundoVoiceChannel;
import eu.brundo.bot.util.BottiResourceBundle;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Random;

public class CanIGoToBedCommand extends AbstractCommand {

    public CanIGoToBedCommand() {
        super("darfIchInsBett");
    }

    @Override
    protected void onCommand(final MessageReceivedEvent event) {
        final MessageChannel channel = event.getChannel();
        final Random random = new Random(System.currentTimeMillis());
        final List<String> messages = new ArrayList<>();
        messages.add(BottiResourceBundle.getMessage("command.darfIchInsBett.antwort1"));
        messages.add(BottiResourceBundle.getMessage("command.darfIchInsBett.antwort2"));
        messages.add(BottiResourceBundle.getMessage("command.darfIchInsBett.antwort3"));
        messages.add(BottiResourceBundle.getMessage("command.darfIchInsBett.antwort4"));

        final List<Member> members = Optional.ofNullable(event.getJDA().getVoiceChannelById(BrundoVoiceChannel.TREFFPUNKT.getId()))
                .map(c -> c.getMembers())
                .orElse(Collections.emptyList());
        if (!members.isEmpty()) {
            final String name = getUserName(getRandomEntry(members));
            messages.add(BottiResourceBundle.getMessage("command.darfIchInsBett.antwort5", name));
            messages.add(BottiResourceBundle.getMessage("command.darfIchInsBett.antwort6", name));
        }
        channel.sendMessage(messages.get(random.nextInt(messages.size()))).queue();
    }

    @Override
    public String getHelp() {
        return BottiResourceBundle.getMessage("command.darfIchInsBett.help");
    }
}
