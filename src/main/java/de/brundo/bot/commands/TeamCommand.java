package de.brundo.bot.commands;

import com.google.common.collect.Iterables;
import de.brundo.bot.AbstractCommand;
import de.brundo.bot.data.BrundoVoiceChannel;
import de.brundo.bot.data.TeamManager;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;

public class TeamCommand extends AbstractCommand {

    public TeamCommand() {
        super("tiername");
    }

    @Override
    protected void onCommand(final MessageReceivedEvent event) {
        final MessageChannel channel = event.getChannel();
        channel.sendMessage("Hier ein cooler Name für dein Team: **" + TeamManager.getInstance().getRandomTeamName() + "**").queue();
    }

    @Override
    public String getHelp() {
        return "Gibt den Namen eines zufälligen Teams aus";
    }
}
