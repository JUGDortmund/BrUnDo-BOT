package eu.brundo.bot.tasks;

import eu.brundo.bot.MongoConnector;
import eu.brundo.bot.util.BottiResourceBundle;
import eu.brundo.bot.util.RandomCollection;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.TextChannel;

import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class RandomChatting extends AbstractTask {

    private final RandomCollection<String> messages;

    private final TextChannel channel;

    private final Random random;

    public RandomChatting(final JDA jda, final MongoConnector mongoConnector) {
        super(jda, mongoConnector);
        final List<String> messagesList = IntStream.range(1, 43)
                .mapToObj(index -> BottiResourceBundle.getMessage("randomChatting.message" + index))
                .collect(Collectors.toList());
        messages = new RandomCollection<>(messagesList);
        channel = jda.getGuilds().stream()
                .flatMap(guild -> guild.getTextChannels().stream())
                .filter(textChannel -> Objects.equals("quassel-zone", textChannel.getName()))
                .findAny()
                .orElseThrow(() -> new IllegalStateException("Can not find Channel 'quassel-zone'"));
        random = new Random(System.currentTimeMillis());
    }

    @Override
    public void run() {
        sleep(1_000_000);
        channel.sendMessage(messages.get()).complete();
        sleep(random.nextInt(24_000_000) + 4_000_000);
    }
}
