package de.brundo.bot;

import de.brundo.bot.commands.AllowDataCollectionCommand;
import de.brundo.bot.commands.CanIGoToBedCommand;
import de.brundo.bot.commands.CustomDice6Command;
import de.brundo.bot.commands.CuteDice6Command;
import de.brundo.bot.commands.Dice10Command;
import de.brundo.bot.commands.Dice6Command;
import de.brundo.bot.commands.DisableDataCollectionCommand;
import de.brundo.bot.commands.HelpCommand;
import de.brundo.bot.commands.KapernCommand;
import de.brundo.bot.commands.QuoteCommand;
import de.brundo.bot.commands.SuggestGameCommand;
import de.brundo.bot.commands.TeamCommand;
import de.brundo.bot.commands.TeamsCommand;
import de.brundo.bot.commands.TieBreakCommand;
import de.brundo.bot.commands.TimerCommand;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;

import javax.security.auth.login.LoginException;
import java.util.ArrayList;
import java.util.List;

public class DiscordBot {

    public DiscordBot(final String discordToken) throws LoginException {
        final JDABuilder builder = JDABuilder.createDefault(discordToken);
        builder.setActivity(Activity.playing("Monopoly"));
        final JDA jda = builder.build();

        final MongoConnector mongoConnector = new MongoConnector();

        final List<AbstractCommand> commands = new ArrayList<>();
        commands.add(new Dice6Command());
        commands.add(new Dice10Command());
        commands.add(new CuteDice6Command());
        commands.add(new CustomDice6Command());
        commands.add(new KapernCommand());
        commands.add(new SuggestGameCommand());
        commands.add(new TeamCommand());
        commands.add(new TeamsCommand());
        commands.add(new CanIGoToBedCommand());
        commands.add(new TimerCommand());
        commands.add(new TieBreakCommand());
        commands.add(new QuoteCommand());
        commands.add(new AllowDataCollectionCommand(mongoConnector));
        commands.add(new DisableDataCollectionCommand(mongoConnector));

        commands.forEach(command -> jda.addEventListener(command));
        jda.addEventListener(new HelpCommand(commands));
        jda.addEventListener(new DebugEventListener());
    }

    public static void main(final String[] args) throws Exception {
        final String discordToken = ApplicationEnvironment.getDiscordToken();
        new DiscordBot(discordToken);
    }
}
