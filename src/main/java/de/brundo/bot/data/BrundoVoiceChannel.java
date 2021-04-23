package de.brundo.bot.data;

import org.checkerframework.checker.nullness.Opt;

import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;

public enum BrundoVoiceChannel {
    TREFFPUNKT("Treffpunkt", "801004696503713826", "treff"),
    TISCH_1("Tisch #1", "801057912231297045", "tisch1"),
    TISCH_2("Tisch #2", "801057949074194454", "tisch2"),
    TISCH_3("Tisch #3", "801058039985078292", "tisch3"),
    TISCH_4("Tisch #4", "801058087070072873", "tisch4"),
    TISCH_5("Tisch #5", "825059212899647530", "tisch5");

    private final String name;

    private final String nick;

    private final String id;

    BrundoVoiceChannel(final String name, final String id, final String nick) {
        this.name = name;
        this.id = id;
        this.nick = nick;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public String getNick() {
        return nick;
    }

    public static Optional<BrundoVoiceChannel> getByNickname(final String nickname) {
        return Arrays.asList(BrundoVoiceChannel.values()).stream()
                .filter(c -> Objects.equals(nickname, c.getNick()))
                .findFirst();
    }
}
