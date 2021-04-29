package eu.brundo.bot.commands;

import eu.brundo.bot.data.Game;
import eu.brundo.bot.util.BottiResourceBundle;
import eu.brundo.bot.util.BrundoUtils;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

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
        messages.add(BottiResourceBundle.getMessage("command.darfIchInsBett.antwort9"));
        messages.add(BottiResourceBundle.getMessage("command.darfIchInsBett.antwort10"));
        messages.add(BottiResourceBundle.getMessage("command.darfIchInsBett.antwort11"));
        messages.add(BottiResourceBundle.getMessage("command.darfIchInsBett.antwort12"));
        messages.add(BottiResourceBundle.getMessage("command.darfIchInsBett.antwort13"));
        messages.add(BottiResourceBundle.getMessage("command.darfIchInsBett.antwort14"));
        messages.add(BottiResourceBundle.getMessage("command.darfIchInsBett.antwort17", Game.MONSTER_FACTORY.getName(), Game.MONSTER_FACTORY.getLink()));

        final List<Member> otherMembers = BrundoUtils.getAllMembersInVoiceChannels(event.getJDA()).stream()
                .filter(member -> !Objects.equals(member, event.getMember()))
                .collect(Collectors.toList());
        if (!otherMembers.isEmpty()) {
            final String name = BrundoUtils.getUserName(getRandomEntry(otherMembers));
            messages.add(BottiResourceBundle.getMessage("command.darfIchInsBett.antwort5", name));
            messages.add(BottiResourceBundle.getMessage("command.darfIchInsBett.antwort6", name));
            messages.add(BottiResourceBundle.getMessage("command.darfIchInsBett.antwort8", name));
            messages.add(BottiResourceBundle.getMessage("command.darfIchInsBett.antwort15", name));
            messages.add(BottiResourceBundle.getMessage("command.darfIchInsBett.antwort16", name));
        } else {
            messages.add(BottiResourceBundle.getMessage("command.darfIchInsBett.antwort7"));
        }
        channel.sendMessage(getRandomEntry(messages)).queue();
    }

    @Override
    public CommandCategories getCategory() {
        return CommandCategories.ADDITIONAL_CATEGORY;
    }
}
