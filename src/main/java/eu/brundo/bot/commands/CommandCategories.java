package eu.brundo.bot.commands;

public enum CommandCategories implements Comparable<CommandCategories> {

    GAME_CATEGORY("Spiele"),
    ACHIEVEMTENT_CATEGORY("Erfolge"),
    ADDITIONAL_CATEGORY("Weiteres");

    private final String name;

    CommandCategories(final String name) {
        this.name = name;
    }

    public String getCategoryName() {
        return name;
    }
}
