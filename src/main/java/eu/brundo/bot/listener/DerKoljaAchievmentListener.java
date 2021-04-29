package eu.brundo.bot.listener;

import eu.brundo.bot.MongoConnector;
import eu.brundo.bot.achievements.DerKoljaAchievment;
import eu.brundo.bot.services.AchievementService;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.stream.IntStream;

public class DerKoljaAchievmentListener extends ListenerAdapter {

    private final AchievementService achievementService;

    public DerKoljaAchievmentListener(final MongoConnector mongoConnector) {
        this.achievementService = new AchievementService(mongoConnector);
    }

    @Override
    public void onMessageReceived(final MessageReceivedEvent event) {
        final String message = event.getMessage().getContentDisplay();
        final int lenght = message.length();
        final String onlyW = IntStream.range(0, lenght).mapToObj(i -> "w").reduce("", (a, b) -> a + b);


        if (message.contains(onlyW)) {
            if (!achievementService.hasAchived(event.getMember(), new DerKoljaAchievment())) {
                achievementService.addAchievement(new DerKoljaAchievment(), event.getMember());
            }
        }
    }
}
