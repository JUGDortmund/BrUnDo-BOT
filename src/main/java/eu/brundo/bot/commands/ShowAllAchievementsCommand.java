package eu.brundo.bot.commands;

import eu.brundo.bot.MongoConnector;
import eu.brundo.bot.services.AchievementService;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class ShowAllAchievementsCommand extends AbstractCommand {

    private final AchievementService achievementService;

    public ShowAllAchievementsCommand(final MongoConnector mongoConnector) {
        super("achievements");
        this.achievementService = new AchievementService(mongoConnector);
    }

    @Override
    protected void onCommand(final MessageReceivedEvent event) {
        final MessageChannel channel = event.getChannel();
        channel.sendMessage("Hier sind alle Erfolge :trophy: die du bekommen kannst:").complete();
        achievementService.getAchievements().forEach(a -> channel.sendMessage("**" + a.getName() + "**: " + a.getDescription()).complete());
    }

    @Override
    public boolean isAllowed(final Member overviewRequester, final MessageChannel channel) {
        return isBottiAdmin(overviewRequester) || isAdmin(overviewRequester);
    }

    @Override
    public String getHelp() {
        return "Zeigt alle Achievements die es gibt";
    }

    @Override
    public CommandCategories getCategory() {
        return CommandCategories.ACHIEVEMTENT_CATEGORY;
    }
}
