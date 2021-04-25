package eu.brundo.bot.commands;

import eu.brundo.bot.AbstractCommand;
import eu.brundo.bot.MongoConnector;
import eu.brundo.bot.repositories.MemberRepository;
import eu.brundo.bot.store.MemberEntity;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.Objects;

public class AllowDataCollectionCommand extends AbstractCommand {

    private final MemberRepository memberRepository;

    public AllowDataCollectionCommand(final MongoConnector mongoConnector) {
        super("allowDataCollection");
        this.memberRepository = new MemberRepository(mongoConnector);
    }

    @Override
    protected void onCommand(final MessageReceivedEvent event) {
        final Member sender = event.getMember();
        final MessageChannel channel = event.getChannel();

        final MemberEntity member = memberRepository.findMemberByDiscordId(sender.getId())
                .orElseGet(() -> memberRepository.createNewEntity(sender));
        if (!member.isCollectingDataAllowed()) {
            member.setCollectingDataAllowed(true);
            memberRepository.save(member);
            channel.sendMessage("Ab jetzt kann ich ich ein paar kleine Daten über dich sammeln um zu entscheiden ob du Achievements :trophy: bekommst :)").complete();
        } else {
            channel.sendMessage("Du hattest dem Sammeln von Daten schon zugestimmt.").complete();
        }
    }

    @Override
    public boolean isAllowed(final Member overviewRequester, final MessageChannel channel) {
        return overviewRequester.getRoles().stream().filter(role -> Objects.equals(role.getName(), "Botti-Admin")).findAny().isPresent();
    }

    @Override
    public String getHelp() {
        return "Um Achievements/Erfolge :trophy: im Discord freizuschalten muss Botti ein paar deiner Daten in einer sicheren Umgebung abspeichern. Mit diesem Command Erlabst du, dass Botti generelle Informationen über deinen User und deine Nutzung von Discord speichern darf." +
                "**Es werden nie persönliche Gespräche oder ähnliche Informationen festgehalten! Ich (Botti) sammel keinerlei Daten von einem Mitglied des Discords bevor es mit nicht explizit erlaubt wurde.**";
    }
}
