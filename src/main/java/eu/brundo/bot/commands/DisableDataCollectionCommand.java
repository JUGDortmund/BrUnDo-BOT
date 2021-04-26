package eu.brundo.bot.commands;

import eu.brundo.bot.AbstractCommand;
import eu.brundo.bot.MongoConnector;
import eu.brundo.bot.entities.MemberEntity;
import eu.brundo.bot.repositories.MemberRepository;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class DisableDataCollectionCommand extends AbstractCommand {

    private final MemberRepository memberRepository;

    public DisableDataCollectionCommand(final MongoConnector mongoConnector) {
        super("disableDataCollection");
        this.memberRepository = new MemberRepository(mongoConnector);
    }

    @Override
    protected void onCommand(final MessageReceivedEvent event) {
        final Member sender = event.getMember();
        final MessageChannel channel = event.getChannel();

        final MemberEntity member = memberRepository.findMemberByDiscordId(sender.getId())
                .orElseGet(() -> memberRepository.createNewEntity(sender));
        if (member.isCollectingDataAllowed()) {
            member.setCollectingDataAllowed(false);
            memberRepository.save(member);
            channel.sendMessage("Ab jetzt sammel ich keine Daten mehr von dir. Du kannst jetzt aber auch keine Achievements :trophy: mehr bekommen").complete();
        } else {
            channel.sendMessage("Du musst nix deaktivieren, da ich eh keine Daten von dir sammel ;).").complete();
        }
    }

    @Override
    public boolean isAllowed(final Member overviewRequester, final MessageChannel channel) {
        return isBottiAdmin(overviewRequester);
    }

    @Override
    public String getHelp() {
        return "Um Achievements/Erfolge :trophy: im Discord freizuschalten muss Botti ein paar deiner Daten in einer sicheren Umgebung abspeichern. Mit diesem Command deaktivierst du da Sammeln von Daten wieder, falls du es in der Vergangenheit aktiviert hast und nun nicht mehr nutzen möchtest." +
                "**Es werden nie persönliche Gespräche oder ähnliche Informationen festgehalten! Ich (Botti) sammel keinerlei Daten von einem Mitglied des Discords bevor es mit nicht explizit erlaubt wurde.**";
    }
}
