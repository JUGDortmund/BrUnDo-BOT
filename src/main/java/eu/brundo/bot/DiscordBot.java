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
import eu.brundo.bot.commands.SuggestGameCommand;
import eu.brundo.bot.commands.TeamCommand;
import eu.brundo.bot.commands.TeamsCommand;
import eu.brundo.bot.commands.TieBreakCommand;
import eu.brundo.bot.commands.TimerCommand;
import eu.brundo.bot.services.ChannelSessionService;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceUpdateEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import javax.annotation.Nonnull;
import javax.security.auth.login.LoginException;
import java.util.ArrayList;
import java.util.List;

public class DiscordBot {

    public DiscordBot(final String discordToken) throws LoginException {
        final JDABuilder builder = JDABuilder.createDefault(discordToken);
        builder.setActivity(Activity.playing("Monopoly"));
        final JDA jda = builder.build();

        final MongoConnector mongoConnector = new MongoConnector();

        final ChannelSessionService channelSessionService = new ChannelSessionService(mongoConnector);

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


        jda.addEventListener(new ListenerAdapter() {

            @Override
            public void onGuildVoiceUpdate(@Nonnull final GuildVoiceUpdateEvent event) {
                final VoiceChannel channelJoined = event.getChannelJoined();
                final VoiceChannel channelLeft = event.getChannelLeft();
                final Member member = event.getEntity();
                if (member != null) {
                    if (channelLeft != null) {
                        channelSessionService.onMemberLeavesChannel(channelLeft, member);
                    }
                    if (channelJoined != null) {
                        channelSessionService.onMemberJoinsChannel(channelJoined, member);
                    }
                }
            }
        });
    }

    public static void main(final String[] args) throws Exception {
        final String discordToken = ApplicationEnvironment.getDiscordToken();
        new DiscordBot(discordToken);
    }
}
