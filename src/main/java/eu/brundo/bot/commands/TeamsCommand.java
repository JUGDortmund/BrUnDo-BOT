package eu.brundo.bot.commands;

import com.google.common.collect.Iterables;
import eu.brundo.bot.MongoConnector;
import eu.brundo.bot.achievements.SpalterAchievment;
import eu.brundo.bot.data.BadnessManager;
import eu.brundo.bot.data.BrundoVoiceChannel;
import eu.brundo.bot.data.TeamManager;
import eu.brundo.bot.services.AchievementService;
import eu.brundo.bot.util.BrundoUtils;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;

public class TeamsCommand extends AbstractCommand {

    private final AchievementService achievementService;

    public TeamsCommand(final MongoConnector mongoConnector) {
        super("teams");
        this.achievementService = new AchievementService(mongoConnector);
    }

    @Override
    protected void onCommand(final MessageReceivedEvent event) {
        final MessageChannel channel = event.getChannel();
        final String message = event.getMessage().getContentDisplay();
        final String substring = message.substring("!teams".length()).trim();
        final BrundoVoiceChannel brundoChannel = Arrays.asList(BrundoVoiceChannel.values()).stream()
                .filter(c -> substring.startsWith(c.getNick()))
                .findAny()
                .orElse(null);
        if (brundoChannel == null) {
            sendMessage(channel, "command.teams.fail1");
            BadnessManager.getInstance().increase(BrundoUtils.getUserName(event.getMember()), 5);
        } else {
            final VoiceChannel voiceChannel = event.getJDA().getVoiceChannelById(brundoChannel.getId());
            final List<Member> members = new ArrayList<>(voiceChannel.getMembers());
            if (members.size() == 0) {
                sendMessage(channel, "command.teams.fail2", brundoChannel.getName());
                BadnessManager.getInstance().increase(BrundoUtils.getUserName(event.getMember()), 5);
            } else {
                Collections.shuffle(members);
                final String countString = substring.substring(brundoChannel.getNick().length()).trim();
                final Consumer<Integer> splitter = count -> {
                    if (members.size() < count) {
                        sendMessage(channel, "command.teams.fail3", brundoChannel.getName(), count);
                        BadnessManager.getInstance().increase(BrundoUtils.getUserName(event.getMember()), 5);
                    }
                    final int round = Math.min(1, Math.floorMod(members.size(), count));
                    final int size = Math.max(1, members.size() / count) + round;
                    final Iterable<List<Member>> partitions = Iterables.partition(members, size);
                    final StringBuilder messageBuilder = new StringBuilder();
                    partitions.forEach(list -> {
                        final String teamMembers = list.stream().map(member -> "<@" + member.getId() + ">").reduce("", (a, b) -> a + ", " + b);
                        messageBuilder.append("**" + TeamManager.getInstance().getRandomTeamName() + "**: " + teamMembers.substring(1)).append(System.lineSeparator());
                    });
                    sendTranslatedMessage(channel, messageBuilder.toString());
                    if (!achievementService.hasAchived(event.getMember(), new SpalterAchievment())) {
                        achievementService.addAchievement(new SpalterAchievment(), event.getMember());
                    }
                };
                if (countString.isEmpty()) {
                    splitter.accept(2);
                } else {
                    try {
                        final int count = Integer.parseInt(countString);
                        splitter.accept(count);
                    } catch (final Exception e) {
                        sendMessage(channel, "command.teams.fail4");
                    }
                }
            }
        }
    }

    @Override
    public CommandCategories getCategory() {
        return CommandCategories.GAME_CATEGORY;
    }
}
