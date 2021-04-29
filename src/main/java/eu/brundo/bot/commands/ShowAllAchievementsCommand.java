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
        final StringBuilder messageBuilder = new StringBuilder();
        messageBuilder.append(translate("command.achievements.answer")).append(System.lineSeparator());
        achievementService.getAchievements().forEach(a -> messageBuilder.append("**" + a.getName() + "**: " + a.getDescription()).append(System.lineSeparator()));
        sendTranslatedMessage(channel, messageBuilder.toString());
    }

    @Override
    public boolean isAllowed(final Member overviewRequester, final MessageChannel channel) {
        return isBottiAdmin(overviewRequester) || isAdmin(overviewRequester);
    }

    @Override
    public CommandCategories getCategory() {
        return CommandCategories.ACHIEVEMTENT_CATEGORY;
    }
}
