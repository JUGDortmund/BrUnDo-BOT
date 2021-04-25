package eu.brundo.bot.achievements;

import net.dv8tion.jda.api.entities.Member;

public abstract class AbstractAchievement {

    private final String id;

    private final String name;

    public AbstractAchievement(final String id, final String name) {
        this.id = id;
        this.name = name;
    }

    public abstract boolean achived(final Member member);

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public abstract String getDescription();
}
