package eu.brundo.bot.commands;

import eu.brundo.bot.data.MissionManager;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.PrivateChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class StartMissionCommand extends AbstractCommand {

    private final Map<String, LocalDateTime> lastUsageCache = new HashMap<>();

    public StartMissionCommand() {
        super("startMission");
    }

    @Override
    protected void onCommand(final MessageReceivedEvent event) {
        final MessageChannel channel = event.getChannel();
        if (channel instanceof PrivateChannel) {
            if (lastUsageCache.containsKey(event.getAuthor().getId())) {
                final LocalDateTime timeStamp = lastUsageCache.get(event.getAuthor().getId());
                final Duration duration = Duration.between(timeStamp, LocalDateTime.now());
                if (duration.toMinutes() < 30) {
                    sendMessage(channel, "command.startMission.fail1");
                    return;
                }
            }
            sendMessage(channel, "command.startMission.answer", MissionManager.getInstance().getRandomMission());
            lastUsageCache.put(event.getAuthor().getId(), LocalDateTime.now());
        } else {
            sendMessage(channel, "command.startMission.fail2");
        }
    }

    @Override
    public CommandCategories getCategory() {
        return CommandCategories.GAME_CATEGORY;
    }
}
