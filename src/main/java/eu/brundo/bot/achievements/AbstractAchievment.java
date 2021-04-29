package eu.brundo.bot.achievements;

import eu.brundo.bot.util.BottiResourceBundle;

public abstract class AbstractAchievment {

    private final String id;

    private final String name;

    private final String description;

    private final int points;

    public AbstractAchievment(final String id, final int points) {
        this.id = id;
        this.name = BottiResourceBundle.getMessage("achievement." + id + ".name");
        this.description = BottiResourceBundle.getMessage("achievement." + id + ".description");
        this.points = points;
    }

    public int getPoints() {
        return points;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

}
