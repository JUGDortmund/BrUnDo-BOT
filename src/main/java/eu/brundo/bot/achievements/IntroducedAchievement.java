package eu.brundo.bot.achievements;

import eu.brundo.bot.MongoConnector;
import eu.brundo.bot.services.ChannelSessionService;
import net.dv8tion.jda.api.entities.Member;

import java.util.Objects;

public class IntroducedAchievement extends AbstractCheckableAchievement {

    private final ChannelSessionService channelSessionService;

    public IntroducedAchievement(final MongoConnector mongoConnector) {
        super("Introduced", "WieEinBunterHund");
        this.channelSessionService = new ChannelSessionService(mongoConnector);
    }

    @Override
    public boolean achived(final Member member) {
        return member.getRoles().stream()
                .map(role -> role.getName())
                .filter(name -> Objects.equals(name, "vorgestellt"))
                .findAny()
                .isPresent();
    }

    @Override
    public String getDescription() {
        return "Wir kennen dich";
    }
}
