package eu.brundo.bot.data;

import java.util.Random;

public enum KapernDice {
    TOTENKOPF(BrundoEmojis.KAPERN_TOTENKOPF_EMOJI),
    DIAMANT(BrundoEmojis.KAPERN_DIAMANT_EMOJI),
    SAEBEL("SAEBEL"),
    AFFE(BrundoEmojis.KAPERN_AFFE_EMOJI),
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
