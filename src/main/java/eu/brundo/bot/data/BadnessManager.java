package eu.brundo.bot.data;

import javax.imageio.ImageIO;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class BadnessManager {

    private final static BadnessManager INSTANCE = new BadnessManager();

    private final Map<String, Integer> badnessMap;

    public BadnessManager() {
        this.badnessMap = new HashMap<>();
    }

    public synchronized Optional<String> findUserThatContains(final String name) {
        final List<String> names = badnessMap.keySet().stream()
                .filter(key -> key.toLowerCase().contains(name.toLowerCase()))
                .collect(Collectors.toList());
        if (names.size() == 1) {
            return Optional.ofNullable(names.get(0));
        }
        return Optional.empty();
    }

    public synchronized int getBadness(final String name) {
        if (!badnessMap.containsKey(name)) {
            if (name.toLowerCase().contains("maschipfen") || name.toLowerCase().contains("merry") || name.toLowerCase().contains("becks2609")) {
                badnessMap.put(name, 75);
            }
            if (name.toLowerCase().contains("kumi") || name.toLowerCase().contains("boardgamepunk") || name.toLowerCase().contains("kolja")) {
                badnessMap.put(name, 0);
            }
            badnessMap.put(name, 15);
        }
        return badnessMap.getOrDefault(name, 0);
    }

    public synchronized void decrease(final String name, final int value) {
        final int currentValue = badnessMap.getOrDefault(name, 0);
        final int newValue = Math.max(0, currentValue - value);
        badnessMap.put(name, newValue);
    }

    public synchronized void decrease(final String name) {
        decrease(name, 5);
    }

    public synchronized void increase(final String name, final int value) {
        final int currentValue = badnessMap.getOrDefault(name, 0);
        final int newValue = Math.min(100, currentValue + value);
        badnessMap.put(name, newValue);
    }

    public synchronized void increase(final String name) {
        increase(name, 10);
    }

    public BufferedImage renderBadness(final String name) {
        try {
            final int badness = getBadness(name);
            final BufferedImage badnessImage = ImageIO.read(BadnessManager.class.getResource("/badness-back.png"));
            final BufferedImage badnessFrontImage = ImageIO.read(BadnessManager.class.getResource("/badness-front.png"));
            final BufferedImage badnessValueImage = ImageIO.read(BadnessManager.class.getResource("/badness-value.png"));
            final Graphics graphics = badnessImage.getGraphics();
            final int yValue = (int) (((100 - badness) / 100.0d) * 425.0d);
            graphics.drawImage(badnessValueImage, 0, yValue, null);
            graphics.drawImage(badnessFrontImage, 0, 0, null);
            return badnessImage;
        } catch (final Exception e) {
            throw new RuntimeException("Can not render image", e);
        }
    }

    public static BadnessManager getInstance() {
        return INSTANCE;
    }
}
