package eu.brundo.bot.commands;

import eu.brundo.bot.MongoConnector;
import eu.brundo.bot.achievements.AbstractAchievment;
import eu.brundo.bot.data.TeamManager;
import eu.brundo.bot.services.AchievementService;
import eu.brundo.bot.util.BrundoUtils;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.jetbrains.annotations.NotNull;

import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class HighscoreCommand extends AbstractCommand {

    private final AchievementService achievementService;

    private final JDA jda;

    public HighscoreCommand(final JDA jda, final MongoConnector mongoConnector) {
        super("highscore");
        this.jda = jda;
        this.achievementService = new AchievementService(mongoConnector);
    }

    @Override
    protected void onCommand(final MessageReceivedEvent event) {
        final MessageChannel channel = event.getChannel();
        final Set<Member> members = new HashSet<>(achievementService.getAllMembersWithAchievments(jda));
        final int count = Math.min(10, members.size());
        final StringBuilder messageBuilder = new StringBuilder();
        messageBuilder.append(translate("command.highscore.answer", count, TeamManager.getInstance().getRandomAdjective(), TeamManager.getInstance().getRandomAdjective(), TeamManager.getInstance().getRandomRace()));
        final List<MemberAndAchievments> sortedUsers = members.stream()
                .map(member -> {
                    final List<AbstractAchievment> achievments = achievementService.getAllForMember(member);
                    return new MemberAndAchievments(member, achievments);
                }).sorted()
                .limit(count).collect(Collectors.toList());

        try {
            final BufferedImage highscoreBackgroundImage = ImageIO.read(HighscoreCommand.class.getResource("/highscore-back.png"));
            final Graphics2D graphics2D = (Graphics2D) highscoreBackgroundImage.getGraphics();
            graphics2D.setColor(Color.RED);
            graphics2D.setRenderingHint(
                    RenderingHints.KEY_TEXT_ANTIALIASING,
                    RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

            if (sortedUsers.size() >= 1) {
                addUser(graphics2D, BrundoUtils.getUserName(sortedUsers.get(0).member), sortedUsers.get(0).getPoints() + "", 300, 320);
            }
            if (sortedUsers.size() >= 2) {
                addUser(graphics2D, BrundoUtils.getUserName(sortedUsers.get(1).member), sortedUsers.get(1).getPoints() + "", 520, 540);
            }
            if (sortedUsers.size() >= 3) {
                addUser(graphics2D, BrundoUtils.getUserName(sortedUsers.get(2).member), sortedUsers.get(2).getPoints() + "", 750, 770);
            }
            if (sortedUsers.size() >= 4) {
                addUser(graphics2D, BrundoUtils.getUserName(sortedUsers.get(3).member), sortedUsers.get(3).getPoints() + "", 930, 930);
            }
            if (sortedUsers.size() >= 5) {
                addUser(graphics2D, BrundoUtils.getUserName(sortedUsers.get(4).member), sortedUsers.get(4).getPoints() + "", 1020, 1030);
            }
            if (sortedUsers.size() >= 6) {
                addUser(graphics2D, BrundoUtils.getUserName(sortedUsers.get(5).member), sortedUsers.get(5).getPoints() + "", 1115, 1125);
            }
            if (sortedUsers.size() >= 7) {
                addUser(graphics2D, BrundoUtils.getUserName(sortedUsers.get(6).member), sortedUsers.get(6).getPoints() + "", 1210, 1220);
            }
            if (sortedUsers.size() >= 8) {
                addUser(graphics2D, BrundoUtils.getUserName(sortedUsers.get(7).member), sortedUsers.get(7).getPoints() + "", 1305, 1315);
            }
            if (sortedUsers.size() >= 9) {
                addUser(graphics2D, BrundoUtils.getUserName(sortedUsers.get(8).member), sortedUsers.get(8).getPoints() + "", 1400, 1410);
            }
            if (sortedUsers.size() >= 10) {
                addUser(graphics2D, BrundoUtils.getUserName(sortedUsers.get(9).member), sortedUsers.get(9).getPoints() + "", 1495, 1505);
            }
            final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            ImageIO.write(highscoreBackgroundImage, "PNG", outputStream);
            channel.sendMessage("Highscore").addFile(outputStream.toByteArray(), "highscore.png").complete();
        } catch (final Exception e) {
            throw new RuntimeException("Error", e);
        }


//            graphics2D.setFont(graphics2D.getFont().deriveFont(32.0f).deriveFont(Font.BOLD));
//        graphics2D.drawString("BoardgamePunk | Hendrik", 260, 300);
//        graphics2D.setFont(graphics2D.getFont().deriveFont(64.0f).deriveFont(Font.BOLD));
//        graphics2D.drawString("120", 780, 320);
//
//        graphics2D.setFont(graphics2D.getFont().deriveFont(32.0f).deriveFont(Font.BOLD));
//        graphics2D.drawString("Hendrik2", 260, 520);
//        graphics2D.setFont(graphics2D.getFont().deriveFont(64.0f).deriveFont(Font.BOLD));
//        graphics2D.drawString("120", 780, 540);
//
//        graphics2D.setFont(graphics2D.getFont().deriveFont(32.0f).deriveFont(Font.BOLD));
//        graphics2D.drawString("Hendrik3", 260, 750);
//        graphics2D.setFont(graphics2D.getFont().deriveFont(64.0f).deriveFont(Font.BOLD));
//        graphics2D.drawString("120", 780, 770);
//
//        graphics2D.setFont(graphics2D.getFont().deriveFont(32.0f).deriveFont(Font.BOLD));
//        graphics2D.drawString("Hendrik4", 260, 930);
//        graphics2D.setFont(graphics2D.getFont().deriveFont(64.0f).deriveFont(Font.BOLD));
//        graphics2D.drawString("120", 780, 930);
//
//        graphics2D.setFont(graphics2D.getFont().deriveFont(32.0f).deriveFont(Font.BOLD));
//        graphics2D.drawString("Hendrik5", 260, 1020);
//        graphics2D.setFont(graphics2D.getFont().deriveFont(64.0f).deriveFont(Font.BOLD));
//        graphics2D.drawString("120", 780, 1030);
//
//        graphics2D.setFont(graphics2D.getFont().deriveFont(32.0f).deriveFont(Font.BOLD));
//        graphics2D.drawString("Hendrik6", 260, 1115);
//        graphics2D.setFont(graphics2D.getFont().deriveFont(64.0f).deriveFont(Font.BOLD));
//        graphics2D.drawString("120", 780, 1125);
//
//        graphics2D.setFont(graphics2D.getFont().deriveFont(32.0f).deriveFont(Font.BOLD));
//        graphics2D.drawString("Hendrik7", 260, 1210);
//        graphics2D.setFont(graphics2D.getFont().deriveFont(64.0f).deriveFont(Font.BOLD));
//        graphics2D.drawString("120", 780, 1220);
//
//        graphics2D.setFont(graphics2D.getFont().deriveFont(32.0f).deriveFont(Font.BOLD));
//        graphics2D.drawString("Hendrik8", 260, 1305);
//        graphics2D.setFont(graphics2D.getFont().deriveFont(64.0f).deriveFont(Font.BOLD));
//        graphics2D.drawString("120", 780, 1315);
//
//        graphics2D.setFont(graphics2D.getFont().deriveFont(32.0f).deriveFont(Font.BOLD));
//        graphics2D.drawString("Hendrik9", 260, 1400);
//        graphics2D.setFont(graphics2D.getFont().deriveFont(64.0f).deriveFont(Font.BOLD));
//        graphics2D.drawString("120", 780, 1410);
//
//        graphics2D.setFont(graphics2D.getFont().deriveFont(32.0f).deriveFont(Font.BOLD));
//        graphics2D.drawString("Hendrik10", 260, 1495);
//        graphics2D.setFont(graphics2D.getFont().deriveFont(64.0f).deriveFont(Font.BOLD));
//        graphics2D.drawString("120", 780, 1505);


//                .forEach(value -> {
//                    messageBuilder.append(System.lineSeparator());
//                    messageBuilder.append(BrundoUtils.getUserName(value.member) + " -> " + value.getPoints());
//                });
        //  sendTranslatedMessage(channel, messageBuilder.toString());
    }

    public void addUser(final Graphics2D graphics2D, final String name, final String score, final int nameY, final int scoreY) {
        graphics2D.setFont(graphics2D.getFont().deriveFont(32.0f).deriveFont(Font.BOLD));
        graphics2D.drawString(name, 260, nameY);
        graphics2D.setFont(graphics2D.getFont().deriveFont(64.0f).deriveFont(Font.BOLD));
        graphics2D.drawString(score, 780, scoreY);
    }


    @Override
    public CommandCategories getCategory() {
        return CommandCategories.ACHIEVEMTENT_CATEGORY;
    }

    private class MemberAndAchievments implements Comparable<MemberAndAchievments> {

        private final Member member;

        private final List<AbstractAchievment> achievments;

        public MemberAndAchievments(final Member member, final List<AbstractAchievment> achievments) {
            this.member = member;
            this.achievments = new ArrayList<>(achievments);
        }

        public Member getMember() {
            return member;
        }

        public List<AbstractAchievment> getAchievments() {
            return Collections.unmodifiableList(achievments);
        }

        public int getPoints() {
            return getAchievments().stream()
                    .mapToInt(achievment -> achievment.getPoints())
                    .sum();
        }

        @Override
        public int compareTo(@NotNull final HighscoreCommand.MemberAndAchievments o) {
            return Optional.ofNullable(o)
                    .map(value -> value.getPoints())
                    .map(points -> Integer.compare(points, getPoints()))
                    .orElse(1);
        }
    }
}
