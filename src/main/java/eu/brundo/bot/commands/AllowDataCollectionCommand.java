package eu.brundo.bot.commands;

import eu.brundo.bot.MongoConnector;
import eu.brundo.bot.entities.MemberEntity;
import eu.brundo.bot.repositories.MemberRepository;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

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
            sendMessage(channel, "command.allowDataCollection.success");
        } else {
            sendMessage(channel, "command.allowDataCollection.fail");
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
