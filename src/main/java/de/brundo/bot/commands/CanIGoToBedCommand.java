package de.brundo.bot.commands;

import de.brundo.bot.AbstractCommand;
import de.brundo.bot.data.BrundoVoiceChannel;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CanIGoToBedCommand extends AbstractCommand {

    public CanIGoToBedCommand() {
        super("darfIchInsBett");
    }

    @Override
    protected void onCommand(final MessageReceivedEvent event) {
        final MessageChannel channel = event.getChannel();
        final Random random = new Random(System.currentTimeMillis());
        final int value = random.nextInt(6) + 1;
        final List<String> messages = new ArrayList<>();
        messages.add("NEIN!");
        messages.add("Noch eine Runde Mars Würfeln!");
        final List<Member> members = event.getJDA().getVoiceChannelById(BrundoVoiceChannel.TREFFPUNKT.getId()).getMembers();
        if(!members.isEmpty()) {
            messages.add("Ach komm, " + getUserName(members.get(random.nextInt(members.size()))) + " ist auch noch wach!");
        }
        messages.add("Aber nur, wenn du auch angesagt hast, dass du grad das letzte Spiel spielst");
        channel.sendMessage(messages.get(random.nextInt(messages.size()))).queue();
    }

    @Override
    public String getHelp() {
        return "Hierdurch wird geklärt, ob jemand ins Bett darf";
    }
}
