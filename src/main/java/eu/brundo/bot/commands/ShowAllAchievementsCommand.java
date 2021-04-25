package eu.brundo.bot.commands;

import eu.brundo.bot.AbstractCommand;
import eu.brundo.bot.achievements.AbstractAchievement;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class ShowAllAchievementsCommand extends AbstractCommand {

    private final List<AbstractAchievement> achievements;

    public ShowAllAchievementsCommand(final List<AbstractAchievement> achievements) {
        super("achievements");
        this.achievements = Collections.unmodifiableList(achievements);
    }

    @Override
    protected void onCommand(final MessageReceivedEvent event) {
        final MessageChannel channel = event.getChannel();
        channel.sendMessage("Hier sind alle Erfolge :trophy: die du bekommen kannst:").complete();
        achievements.forEach(a -> channel.sendMessage("**" + a.getName() + "**: " + a.getDescription()).complete());
    }

    @Override
    public boolean isAllowed(final Member overviewRequester, final MessageChannel channel) {
        return overviewRequester.getRoles().stream().filter(role -> Objects.equals(role.getName(), "Botti-Admin")).findAny().isPresent();
    }
    
    @Override
    public String getHelp() {
        return "Zeigt alle Achievements die es gibt";
    }
}
