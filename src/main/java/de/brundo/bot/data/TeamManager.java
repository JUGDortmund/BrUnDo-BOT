package de.brundo.bot.data;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class TeamManager {

    private final static TeamManager INSTANCE = new TeamManager();

    private final List<String> adjectives;

    private final List<String> names;

    private final Random random;

    public TeamManager() {
        random = new Random(System.currentTimeMillis());
        try {
            this.adjectives =  Files.lines(Path.of(TeamManager.class.getResource("/adjektive.txt").toURI()), StandardCharsets.UTF_8).collect(Collectors.toList());
            this.names =  Files.lines(Path.of(TeamManager.class.getResource("/namen.txt").toURI()), StandardCharsets.UTF_8).collect(Collectors.toList());
        } catch (Exception e) {
           throw new RuntimeException("Error", e);
        }
    }

    public String getRandomAdjective() {
        return adjectives.get(random.nextInt(adjectives.size()));
    }

    public String getRandomRace() {
        return names.get(random.nextInt(names.size()));
    }

    public String getRandomTeamName() {
            return "Die " + adjectives.get(random.nextInt(adjectives.size())) + " " + names.get(random.nextInt(names.size()));
    }


    public static TeamManager getInstance() {
        return INSTANCE;
    }
}
