package eu.brundo.bot;

import eu.brundo.bot.commands.AllowDataCollectionCommand;
import eu.brundo.bot.commands.CanIGoToBedCommand;
import eu.brundo.bot.commands.CustomDice6Command;
import eu.brundo.bot.commands.CuteDice6Command;
import eu.brundo.bot.commands.Dice10Command;
import eu.brundo.bot.commands.Dice6Command;
import eu.brundo.bot.commands.DisableDataCollectionCommand;
import eu.brundo.bot.commands.HelpCommand;
import eu.brundo.bot.commands.KapernCommand;
import eu.brundo.bot.commands.QuoteCommand;
import eu.brundo.bot.commands.ShowAllAchievementsCommand;
import eu.brundo.bot.commands.ShowMyAchievementsCommand;
import eu.brundo.bot.commands.SuggestGameCommand;
import eu.brundo.bot.commands.TeamCommand;
import eu.brundo.bot.commands.TeamsCommand;
import eu.brundo.bot.commands.TieBreakCommand;
import eu.brundo.bot.commands.TimerCommand;
import eu.brundo.bot.listener.ChannelSessionListener;
import eu.brundo.bot.listener.DebugEventListener;
import eu.brundo.bot.services.AchievementService;
import eu.brundo.bot.services.MemberService;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.Member;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.security.auth.login.LoginException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

public class DiscordBot {

    private final static Logger LOG = LoggerFactory.getLogger(DiscordBot.class);

    public DiscordBot(final String discordToken) throws LoginException {
        final JDABuilder builder = JDABuilder.createDefault(discordToken);
        builder.setActivity(Activity.playing("Monopoly"));
        final JDA jda = builder.build();

        final MongoConnector mongoConnector = new MongoConnector();
        final MemberService memberService = new MemberService(mongoConnector);
        final AchievementService achievementService = new AchievementService(mongoConnector);

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
        commands.add(new ShowAllAchievementsCommand(mongoConnector));
        commands.add(new ShowMyAchievementsCommand(mongoConnector));
        commands.add(new AllowDataCollectionCommand(mongoConnector));
        commands.add(new DisableDataCollectionCommand(mongoConnector));
        commands.forEach(command -> jda.addEventListener(command));
        jda.addEventListener(new HelpCommand(commands));
        jda.addEventListener(new DebugEventListener());
        jda.addEventListener(new ChannelSessionListener(mongoConnector));

        Executors.newSingleThreadExecutor().submit(() -> {
            while (true) {
                final List<Member> knownMembers = memberService.getAllMembers(jda);
                achievementService.checkAll(knownMembers);

                try {
                    Thread.sleep(5_000);
                } catch (final Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    public static void main(final String[] args) throws Exception {
        final String discordToken = ApplicationEnvironment.getDiscordToken();
        new DiscordBot(discordToken);
    }
}
