package eu.brundo.bot.commands;

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
            sendMessage(channel, "command.disableDataCollection.success");
        } else {
            sendMessage(channel, "command.disableDataCollection.fail");
        }
    }

    @Override
    public boolean isAllowed(final Member overviewRequester, final MessageChannel channel) {
        return isBottiAdmin(overviewRequester) || isAdmin(overviewRequester);
    }

    @Override
    public CommandCategories getCategory() {
        return CommandCategories.ACHIEVEMTENT_CATEGORY;
    }
}
