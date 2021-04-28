package eu.brundo.bot.achievements;

public class TieWonAchievment extends AbstractAchievment {

    public TieWonAchievment() {
        super("TieWon", "BesserAlsNix");
    }

    @Override
    public String getDescription() {
        return "Du bist aus einem Unentschieden als Sieger rausgegangen";
    }
}
