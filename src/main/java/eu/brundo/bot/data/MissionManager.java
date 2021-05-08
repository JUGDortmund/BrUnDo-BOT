package eu.brundo.bot.data;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

public class MissionManager {

    private final static MissionManager INSTANCE = new MissionManager();

    private final List<String> missionStarts;

    private final List<String> missionEnds;

    private final List<String> usedMissionStarts;

    private final List<String> usedMissionEnds;

    private final Random random;

    public MissionManager() {
        random = new Random(System.currentTimeMillis());
        try {
            this.missionStarts = Files.lines(Path.of(MissionManager.class.getResource("/mission-start.txt").toURI()), StandardCharsets.UTF_8).collect(Collectors.toList());
            this.missionEnds = Files.lines(Path.of(MissionManager.class.getResource("/mission-end.txt").toURI()), StandardCharsets.UTF_8).collect(Collectors.toList());
        } catch (final Exception e) {
            throw new RuntimeException("Error", e);
        }
        usedMissionStarts = new CopyOnWriteArrayList<>();
        usedMissionEnds = new CopyOnWriteArrayList<>();
    }

    private String getMissionStart() {
        final List<String> unused = new ArrayList<>(missionStarts.stream().filter(s -> !usedMissionStarts.contains(s)).collect(Collectors.toList()));
        if (unused.isEmpty()) {
            usedMissionStarts.clear();
            unused.addAll(missionStarts);
        }
        final String start = unused.get(random.nextInt(unused.size()));
        usedMissionStarts.add(start);
        return start;
    }

    private String getMissionEnd() {
        final List<String> unused = new ArrayList<>(missionEnds.stream().filter(s -> !usedMissionEnds.contains(s)).collect(Collectors.toList()));
        if (unused.isEmpty()) {
            usedMissionEnds.clear();
            unused.addAll(missionEnds);
        }
        final String end = unused.get(random.nextInt(unused.size()));
        usedMissionEnds.add(end);
        return end;
    }

    public String getRandomMission() {

        return getMissionStart() + " " + getMissionEnd();
    }

    public static MissionManager getInstance() {
        return INSTANCE;
    }
}
