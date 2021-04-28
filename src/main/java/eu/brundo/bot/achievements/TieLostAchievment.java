package eu.brundo.bot.achievements;

public class TieLostAchievment extends AbstractAchievment {

    public TieLostAchievment() {
        super("TieLost", "NochWenigerAlsNix");
    }

    @Override
    public String getDescription() {
        return "Hättest du es mal bei einem Unentschieden gelassen...";
    }
}
