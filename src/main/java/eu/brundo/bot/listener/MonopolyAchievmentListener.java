package eu.brundo.bot.listener;

import eu.brundo.bot.MongoConnector;
import eu.brundo.bot.achievements.MonopolyAchievment;
import eu.brundo.bot.services.AchievementService;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MonopolyAchievmentListener extends ListenerAdapter {

    private final static Logger LOG = LoggerFactory.getLogger(MonopolyAchievmentListener.class);

    private final AchievementService achievementService;

    public MonopolyAchievmentListener(final MongoConnector mongoConnector) {
        this.achievementService = new AchievementService(mongoConnector);
    }

    @Override
    public void onMessageReceived(final MessageReceivedEvent event) {
        final String message = event.getMessage().getContentDisplay();
        if (message.contains("Monopoly")) {
            if (!achievementService.hasAchived(event.getMember(), new MonopolyAchievment())) {
                achievementService.addAchievement(new MonopolyAchievment(), event.getMember());
            }
        }
    }
}
