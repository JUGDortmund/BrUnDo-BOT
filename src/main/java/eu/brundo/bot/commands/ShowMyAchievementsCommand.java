package eu.brundo.bot.commands;

import eu.brundo.bot.MongoConnector;
import eu.brundo.bot.achievements.AbstractAchievment;
import eu.brundo.bot.services.AchievementService;
import eu.brundo.bot.services.MemberService;
import eu.brundo.bot.util.BrundoUtils;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.List;

public class ShowMyAchievementsCommand extends AbstractCommand {

    private final AchievementService achievementService;

    private final MemberService memberService;

    public ShowMyAchievementsCommand(final MongoConnector mongoConnector) {
        super("myAchievements");
        this.achievementService = new AchievementService(mongoConnector);
        this.memberService = new MemberService(mongoConnector);
    }

    @Override
    protected void onCommand(final MessageReceivedEvent event) {
        final MessageChannel channel = event.getChannel();
        if (!memberService.isCollectingDataAllowed(event.getMember())) {
            sendMessage(channel, "command.myAchievements.fail1");
            return;
        }
        final List<AbstractAchievment> achievements = achievementService.getAllForMember(event.getMember());
        if (!achievements.isEmpty()) {
            final StringBuilder messageBuilder = new StringBuilder();
            messageBuilder.append(translate("command.myAchievements.answer1", BrundoUtils.getUserName(event.getMember())));
            messageBuilder.append(System.lineSeparator());
            achievements.forEach(achievement -> messageBuilder.append("**" + achievement.getName() + "**: " + achievement.getDescription()).append(System.lineSeparator()));
            final int points = achievements.stream().mapToInt(achievement -> achievement.getPoints()).sum();
            messageBuilder.append(translate("command.myAchievements.answer2", points));
            sendTranslatedMessage(channel, messageBuilder.toString());
        } else {
            sendMessage(channel, "command.myAchievements.fail2");
        }
    }

    @Override
    public CommandCategories getCategory() {
        return CommandCategories.ACHIEVEMTENT_CATEGORY;
    }
}
