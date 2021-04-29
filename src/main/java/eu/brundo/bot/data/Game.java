package eu.brundo.bot.data;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public enum Game {

    KAPERN("Piraten Kapern", 2, 5, "https://boardgamearena.com/gamepanel?game=piratenkapern"),
    CU_BIRDS("CuBirds", 2, 5, "https://boardgamearena.com/gamepanel?game=cubirds"),
    SECHS_NIMMT("6 nimmt!", 2, 10, "https://boardgamearena.com/gamepanel?game=sechsnimmt"),
    MARS_WUERFEL("Mars Würfel", 2, 10, "https://boardgamearena.com/gamepanel?game=martiandice"),
    DIAMANT("Diamant", 3, 8, "https://boardgamearena.com/gamepanel?game=incangold"),
    WIZARD("Wizard", 3, 6, "https://boardgamearena.com/gamepanel?game=wizard"),
    SABOTEUR("Saboteur", 3, 10, "https://boardgamearena.com/gamepanel?game=saboteur"),
    GARLIC_PHONE("GarticPhone", 6, 14, "https://garticphone.com/de"),
    MONSTER_FACTORY("Monster Factory", 2, 6, "https://boardgamearena.com/gamepanel?game=monsterfactory"),
    CODENAMES("Codenames", 6, 16, "https://codenames.game");

    private final String name;

    private final String link;

    private final int minPlayerCount;

    private final int maxPlayerCount;

    Game(final String name, final int minPlayerCount, final int maxPlayerCount, final String link) {
        this.name = name;
        this.minPlayerCount = minPlayerCount;
        this.maxPlayerCount = maxPlayerCount;
        this.link = link;
    }

    public String getName() {
        return name;
    }

    public int getMinPlayerCount() {
        return minPlayerCount;
    }

    public int getMaxPlayerCount() {
        return maxPlayerCount;
    }

    public String getLink() {
        return link;
    }

    public static Optional<Game> randomForPlayers(final int playerCount) {
        final List<Game> games = Arrays.asList(Game.values());
        Collections.shuffle(games);
        return games.stream().filter(game -> game.getMinPlayerCount() <= playerCount)
                .filter(game -> game.getMaxPlayerCount() >= playerCount)
                .findAny();
    }
}
