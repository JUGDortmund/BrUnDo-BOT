package eu.brundo.bot.data;

import java.util.Random;

import eu.brundo.bot.BrundoEmojis;

public enum KapernDice {
    TOTENKOPF(BrundoEmojis.KAPERN_TOTENKOPF),
    DIAMANT(BrundoEmojis.KAPERN_DIAMANT),
    SAEBEL("SAEBEL"),
    AFFE(BrundoEmojis.KAPERN_AFFE),
    PAPAGEI("PAPAGEI"),
    GOLD("GOLD");

    private final static Random random = new Random(System.currentTimeMillis());

    private final String definition;

    KapernDice(final String definition) {
        this.definition = definition;
    }

    public String getDefinition() {
        return definition;
    }

    public static KapernDice random() {
        return KapernDice.values()[random.nextInt(KapernDice.values().length)];
    }
}
