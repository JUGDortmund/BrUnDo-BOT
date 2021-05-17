package eu.brundo.bot;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

public class HighscoreTest {

    public static void main(final String[] args) throws Exception {

        final BufferedImage highscoreBackgroundImage = ImageIO.read(HighscoreTest.class.getResource("/highscore-back.png"));

        System.out.println("Width: " + highscoreBackgroundImage.getWidth());
        System.out.println("Height: " + highscoreBackgroundImage.getHeight());

        final Graphics2D graphics2D = (Graphics2D) highscoreBackgroundImage.getGraphics();
        graphics2D.setColor(Color.RED);
        graphics2D.setFont(graphics2D.getFont().deriveFont(32.0f).deriveFont(Font.BOLD));
        graphics2D.setRenderingHint(
                RenderingHints.KEY_TEXT_ANTIALIASING,
                RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        graphics2D.drawString("BoardgamePunk | Hendrik", 260, 300);
        graphics2D.drawString("Hendrik2", 260, 520);
        graphics2D.drawString("Hendrik3", 260, 750);
        graphics2D.drawString("Hendrik4", 260, 930);
        graphics2D.drawString("Hendrik5", 260, 1020);
        graphics2D.drawString("Hendrik6", 260, 1115);
        graphics2D.drawString("Hendrik7", 260, 1210);
        graphics2D.drawString("Hendrik8", 260, 1305);
        graphics2D.drawString("Hendrik9", 260, 1400);
        graphics2D.drawString("Hendrik10", 260, 1495);

        graphics2D.setFont(graphics2D.getFont().deriveFont(64.0f).deriveFont(Font.BOLD));
        graphics2D.drawString("120", 780, 320);
        graphics2D.drawString("120", 780, 540);
        graphics2D.drawString("120", 780, 770);
        graphics2D.drawString("120", 780, 930);
        graphics2D.drawString("120", 780, 1030);
        graphics2D.drawString("120", 780, 1125);
        graphics2D.drawString("120", 780, 1220);
        graphics2D.drawString("120", 780, 1315);
        graphics2D.drawString("120", 780, 1410);
        graphics2D.drawString("120", 780, 1505);
        

        SwingUtilities.invokeLater(() -> {
            final JFrame frame = new JFrame("Image");
            final JLabel label = new JLabel();
            label.setIcon(new ImageIcon(highscoreBackgroundImage));
            frame.getContentPane().add(label);
            frame.setSize(800, 600);
            frame.setVisible(true);
        });
    }

}
