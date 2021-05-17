package eu.brundo.bot.commands;

import eu.brundo.bot.data.BadnessManager;
import eu.brundo.bot.util.BrundoUtils;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.Objects;

public class BadnessCommand extends AbstractCommand {

    public BadnessCommand() {
        super("bosheit");
    }

    @Override
    protected void onCommand(final MessageReceivedEvent event) {
        final MessageChannel channel = event.getChannel();
        try {
            final List<Member> mentionedMembers = event.getMessage().getMentionedMembers();
            if (mentionedMembers.isEmpty()) {
                sendMessage(channel, "command.bosheit.fail1");
            } else if (mentionedMembers.size() != 1) {
                sendMessage(channel, "command.bosheit.fail2");
            } else {
                final Member member = mentionedMembers.get(0);

                if (Objects.equals("BrUnDo Botti", member.getNickname())) {
                    sendMessage(channel, "command.bosheit.fail3");
                    BadnessManager.getInstance().increase(BrundoUtils.getUserName(event.getMember()), 25);
                } else {
                    final BufferedImage badnessImage = BadnessManager.getInstance().renderBadness(BrundoUtils.getUserName(member));
                    final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                    ImageIO.write(badnessImage, "PNG", outputStream);
                    channel.sendMessage(":robot:").addFile(outputStream.toByteArray(), "bosheit.png").complete();
                }
            }
        } catch (final Exception e) {
            throw new RuntimeException("Error", e);
        }
    }

    @Override
    public CommandCategories getCategory() {
        return CommandCategories.ADDITIONAL_CATEGORY;
    }
}
