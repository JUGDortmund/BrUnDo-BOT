package eu.brundo.bot.achievements;

public abstract class AbstractAchievment {

    private final String id;

    private final String name;

    public AbstractAchievment(final String id, final String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public abstract String getDescription();
}
