package eu.brundo.bot.achievements;

import net.dv8tion.jda.api.entities.Member;

public abstract class AbstractCheckableAchievement extends AbstractAchievment {

    public AbstractCheckableAchievement(final String id, final int points) {
        super(id, points);
    }

    public abstract boolean achived(final Member member);
}
