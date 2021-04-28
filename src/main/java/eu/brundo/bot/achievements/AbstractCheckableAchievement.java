package eu.brundo.bot.achievements;

import net.dv8tion.jda.api.entities.Member;

public abstract class AbstractCheckableAchievement extends AbstractAchievment {

    public AbstractCheckableAchievement(final String id, final String name) {
        super(id, name);
    }

    public abstract boolean achived(final Member member);
}
