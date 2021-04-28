package eu.brundo.bot.achievements;

public class MonopolyAchievment extends AbstractAchievment {

    public MonopolyAchievment() {
        super("Monopoly", "DerVerboteneName");
    }

    @Override
    public String getDescription() {
        return "Du nennst das Spiel dessen Namen nicht genannt werden darf";
    }
}
