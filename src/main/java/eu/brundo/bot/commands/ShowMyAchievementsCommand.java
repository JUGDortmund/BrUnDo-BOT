package eu.brundo.bot.commands;

import eu.brundo.bot.MongoConnector;
import eu.brundo.bot.achievements.AbstractAchievment;
import eu.brundo.bot.services.AchievementService;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.List;

public class ShowMyAchievementsCommand extends AbstractCommand {

    private final AchievementService achievementService;

    public ShowMyAchievementsCommand(final MongoConnector mongoConnector) {
        super("myAchievements");
        this.achievementService = new AchievementService(mongoConnector);
    }

    @Override
    protected void onCommand(final MessageReceivedEvent event) {
        final MessageChannel channel = event.getChannel();
        final List<AbstractAchievment> achievements = achievementService.getAllForMember(event.getMember());
        if (!achievements.isEmpty()) {
            channel.sendMessage("Hi " + getUserName(event.getMember()) + " hier sind alle deine bisherigen Erfolge :trophy::").complete();
            achievements.forEach(achievement -> channel.sendMessage("**" + achievement.getName() + "**: " + achievement.getDescription()).complete());
        } else {
            channel.sendMessage("Achievements? Du? Ähhhh, also da finde ich wirklich nix.").complete();
        }
    }

    @Override
    public boolean isAllowed(final Member overviewRequester, final MessageChannel channel) {
        return isBottiAdmin(overviewRequester) || isAdmin(overviewRequester);
    }

    @Override
    public String getHelp() {
        return "Zeigt alle deine bisher erhaltenen Achievements an";
    }

    @Override
    public CommandCategories getCategory() {
        return CommandCategories.ACHIEVEMTENT_CATEGORY;
    }
}
