package eu.brundo.bot.commands;

import eu.brundo.bot.MongoConnector;
import eu.brundo.bot.achievements.AssasinAchievement;
import eu.brundo.bot.achievements.AssasinatedAchievement;
import eu.brundo.bot.services.AchievementService;
import eu.brundo.bot.util.BrundoUtils;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.List;

public class MissionAccomplishedCommand extends AbstractCommand {

    private final AchievementService achievementService;

    public MissionAccomplishedCommand(final MongoConnector mongoConnector) {
        super("missionAccomplished");
        this.achievementService = new AchievementService(mongoConnector);
    }

    @Override
    public boolean isAllowed(final Member overviewRequester, final MessageChannel channel) {
        return isAdmin(overviewRequester);
    }

    @Override
    protected void onCommand(final MessageReceivedEvent event) {
        final MessageChannel channel = event.getChannel();
        final List<Member> mentionedMembers = event.getMessage().getMentionedMembers();
        if (mentionedMembers.isEmpty()) {
            sendMessage(channel, "command.missionAccomplished.fail");
        } else if (mentionedMembers.size() == 2) {
            sendMessage(channel, "command.missionAccomplished.answer", BrundoUtils.getUserName(mentionedMembers.get(0)), BrundoUtils.getUserName(mentionedMembers.get(1)));
            if (!achievementService.hasAchived(mentionedMembers.get(0), new AssasinAchievement())) {
                achievementService.addAchievement(new AssasinAchievement(), mentionedMembers.get(0));
            }
            if (!achievementService.hasAchived(mentionedMembers.get(1), new AssasinatedAchievement())) {
                achievementService.addAchievement(new AssasinatedAchievement(), mentionedMembers.get(1));
            }
        } else {
            sendMessage(channel, "command.missionAccomplished.fail");
        }
    }

    @Override
    public CommandCategories getCategory() {
        return CommandCategories.GAME_CATEGORY;
    }
}
