package eu.brundo.bot.commands;

import eu.brundo.bot.data.BrundoEmojis;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.Objects;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

public class TimerCommand extends AbstractCommand {

    private final static Logger LOG = LoggerFactory.getLogger(TimerCommand.class);

    private final static int ICON_COUNT = 10;

    public TimerCommand() {
        super("timer");
    }

    @Override
    protected void onCommand(final MessageReceivedEvent event) {
        final MessageChannel channel = event.getChannel();
        final String message = event.getMessage().getContentDisplay();
        final String substring = message.substring("!timer".length()).trim();
        if (substring.isEmpty()) {
            channel.sendMessage("Du musst eine Einheit (Minuten / Sekunden) angeben. Beispiel '!timer 40s' oder !timer 2m'").queue();
        } else {
            final String unit = substring.substring(substring.length() - 1).trim();
            final String countString = substring.substring(0, substring.length() - 1).trim();

            try {
                final int count = Integer.parseInt(countString);
                if (count < 0) {
                    channel.sendMessage("Botti kann nicht mit negativen Zahlen umgehen").queue();
                }
                if (Objects.equals("s", unit)) {
                    if (count > 1_000) {
                        channel.sendMessage("Das ist Botti viel zu lange").queue();
                    } else {
                        Executors.newSingleThreadExecutor().submit(() -> {
                            startTimer(Duration.of(count, ChronoUnit.SECONDS), channel);
                        });
                    }
                } else if (Objects.equals("m", unit)) {
                    if (count > 10) {
                        channel.sendMessage("Das ist Botti viel zu lange").queue();
                    } else {
                        Executors.newSingleThreadExecutor().submit(() -> {
                            startTimer(Duration.of(count, ChronoUnit.MINUTES), channel);
                        });
                    }
                } else {
                    channel.sendMessage("Du musst eine Einheit (Minuten / Sekunden) angeben. Beispiel '!timer 40s' oder !timer 2m'").queue();
                }
            } catch (final Exception e) {
                channel.sendMessage("Du musst eine Einheit (Minuten / Sekunden) angeben. Beispiel '!timer 40s' oder !timer 2m'").queue();
            }
        }
    }

    private void startTimer(final Duration duration, final MessageChannel channel) {
        final LocalTime startTime = LocalTime.now();
        final LocalTime endTime = startTime.plus(duration);
        channel.sendMessage("Timer startet!").complete();
        final String messageId = channel.sendMessage("_________________________").complete().getId();
        while (LocalTime.now().isBefore(endTime)) {
            final Duration untilNow = Duration.between(startTime, LocalTime.now());
            final double percentageDone = (untilNow.toMillis() * 100.0d) / duration.toMillis();
            final int counter = (int) (percentageDone / (100 / ICON_COUNT));
            final String skulls = IntStream.range(0, counter).mapToObj(i -> BrundoEmojis.KAPERN_TOTENKOPF_EMOJI).reduce("", (a, b) -> a + b);
            final String diamonds = IntStream.range(0, ICON_COUNT - counter).mapToObj(i -> BrundoEmojis.KAPERN_DIAMANT_EMOJI).reduce("", (a, b) -> a + b);
            channel.editMessageById(messageId, skulls + diamonds).complete();
            LOG.info("Timer at {} % + counter at {}", percentageDone, counter);
            try {
                final long sleepTime = Math.abs(Math.min(1_000, duration.dividedBy(10).toMillis()));
                Thread.sleep(sleepTime);
            } catch (final InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        final String skulls = IntStream.range(0, ICON_COUNT).mapToObj(i -> BrundoEmojis.KAPERN_TOTENKOPF_EMOJI).reduce("", (a, b) -> a + b);
        channel.sendMessage("ALARM! ALARM!").complete();
        channel.editMessageById(messageId, skulls).complete();
    }

    @Override
    public String getHelp() {
        return "Einen Timer starten";
    }

    @Override
    public CommandCategories getCategory() {
        return CommandCategories.GAME_CATEGORY;
    }
}
