package eu.brundo.bot;

import eu.brundo.bot.commands.AboutDataCommand;
import eu.brundo.bot.commands.AbstractCommand;
import eu.brundo.bot.commands.AllowDataCollectionCommand;
import eu.brundo.bot.commands.BadnessCommand;
import eu.brundo.bot.commands.CanIGoToBedCommand;
import eu.brundo.bot.commands.CustomDice6Command;
import eu.brundo.bot.commands.CuteDice6Command;
import eu.brundo.bot.commands.DecreaseBadnessCommand;
import eu.brundo.bot.commands.Dice10Command;
import eu.brundo.bot.commands.Dice6Command;
import eu.brundo.bot.commands.DisableDataCollectionCommand;
import eu.brundo.bot.commands.HelpCommand;
import eu.brundo.bot.commands.HighscoreCommand;
import eu.brundo.bot.commands.ILikeBottiCommand;
import eu.brundo.bot.commands.IncreaseBadnessCommand;
import eu.brundo.bot.commands.KapernCommand;
import eu.brundo.bot.commands.PennyCommand;
import eu.brundo.bot.commands.PunkiCommand;
import eu.brundo.bot.commands.QuoteCommand;
import eu.brundo.bot.commands.ShowAllAchievementsCommand;
import eu.brundo.bot.commands.ShowMyAchievementsCommand;
import eu.brundo.bot.commands.SpitznameCommand;
import eu.brundo.bot.commands.SuggestGameCommand;
import eu.brundo.bot.commands.TeamCommand;
import eu.brundo.bot.commands.TeamsCommand;
import eu.brundo.bot.commands.TieBreakCommand;
import eu.brundo.bot.commands.TimerCommand;
import eu.brundo.bot.commands.WizardSchummelnCommand;
import eu.brundo.bot.listener.ChannelSessionListener;
import eu.brundo.bot.listener.DerKoljaAchievmentListener;
import eu.brundo.bot.listener.MonopolyAchievmentListener;
import eu.brundo.bot.listener.YoloListener;
import eu.brundo.bot.tasks.AbstractTask;
import eu.brundo.bot.tasks.AchievementCheck;
import eu.brundo.bot.tasks.RandomChatting;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.hooks.EventListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.security.auth.login.LoginException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DiscordBot {

    private final static Logger LOG = LoggerFactory.getLogger(DiscordBot.class);

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
        commands.add(new SuggestGameCommand(mongoConnector));
        commands.add(new TeamCommand());
        commands.add(new TeamsCommand(mongoConnector));
        commands.add(new CanIGoToBedCommand());
        commands.add(new TimerCommand());
        commands.add(new TieBreakCommand(mongoConnector));
        commands.add(new AboutDataCommand());
        commands.add(new ILikeBottiCommand());
        commands.add(new QuoteCommand(mongoConnector));
        commands.add(new ShowAllAchievementsCommand(mongoConnector));
        commands.add(new ShowMyAchievementsCommand(mongoConnector));
        commands.add(new WizardSchummelnCommand());
        commands.add(new HighscoreCommand(jda, mongoConnector));
        commands.add(new AllowDataCollectionCommand(mongoConnector));
        commands.add(new DisableDataCollectionCommand(mongoConnector));
        commands.add(new BadnessCommand());
        commands.add(new IncreaseBadnessCommand());
        commands.add(new DecreaseBadnessCommand());
        commands.add(new PennyCommand());
        commands.add(new PunkiCommand());
        commands.add(new SpitznameCommand());

//        final Parser parser = new Parser(new AchievementService(mongoConnector));
//        try {
//            commands.add(parser.parse(Paths.get(BadnessManager.class.getResource("/hello-command.txt").toURI())));
//            commands.add(parser.parse(Paths.get(BadnessManager.class.getResource("/hi-command.txt").toURI())));
//            commands.add(parser.parse(Paths.get(BadnessManager.class.getResource("/gruss-command.txt").toURI())));
//            commands.add(parser.parse(Paths.get(BadnessManager.class.getResource("/zufall-command.txt").toURI())));
//        } catch (final IOException e) {
//            e.printStackTrace();
//        } catch (final URISyntaxException e) {
//            e.printStackTrace();
//        }


        final List<EventListener> eventListeners = new ArrayList<>();
        eventListeners.addAll(commands);
        eventListeners.add(new HelpCommand(commands));
        eventListeners.add(new ChannelSessionListener(mongoConnector));
        eventListeners.add(new MonopolyAchievmentListener(mongoConnector));
        eventListeners.add(new DerKoljaAchievmentListener(mongoConnector));
        eventListeners.add(new YoloListener());
        eventListeners.forEach(eventListener -> jda.addEventListener(eventListener));

        final List<AbstractTask> tasks = new ArrayList<>();
        tasks.add(new AchievementCheck(jda, mongoConnector));
        tasks.add(new RandomChatting(jda, mongoConnector));
        final ExecutorService executorService = Executors.newCachedThreadPool();
        tasks.forEach(task -> {
            executorService.submit(() -> {
                while (true) {
                    try {
                        task.run();
                    } catch (final Exception exception) {
                        LOG.error("Error while executing task!", exception);
                    }
                }
            });
        });
    }

    public static void main(final String[] args) throws Exception {
        final String discordToken = ApplicationEnvironment.getDiscordToken();
        new DiscordBot(discordToken);
    }
}
