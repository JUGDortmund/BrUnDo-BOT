package eu.brundo.bot.commands;

import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Executors;
import eu.brundo.bot.AbstractCommand;
import eu.brundo.bot.data.Game;
import eu.brundo.bot.data.TeamManager;

public class TieBreakCommand extends AbstractCommand {

    private final static int MAX_SLEEP_TIME = 8_000;

    public TieBreakCommand() {
        super("tiebreak");
    }

    @Override
    protected void onCommand(final MessageReceivedEvent event) {
        final MessageChannel channel = event.getChannel();
        final List<Member> mentionedMembers = event.getMessage().getMentionedMembers();
        if(mentionedMembers.isEmpty()) {
            channel.sendMessage("Du musst schon User angegeben zwischen denen ich entscheiden soll. Etwa so: '!tiebreak @User1 @User2'").queue();
        } else if(mentionedMembers.size() == 1) {
            channel.sendMessage("Es tut mir leid aber meine Schaltkreise bestehen nur aus Catan Schafen und können nicht mit gespaltenen Persönlichkeiten umgehen. Bitte gib wenigstens 2 User an zwischen denen ich vermitteln soll.  Etwa so: '!tiebreak @User1 @User2'").queue();
        } else {
            final Random random = new Random(System.currentTimeMillis());
            final List<String> working = new ArrayList<>();
            working.add("Ich befrage die klügsten aller " + TeamManager.getInstance().getRandomRace() + " um Rat...");
            working.add("Ich sinniere auf einem Turm aus " + (random.nextInt(21) + 1)+ " Würfeln um eine faire Lösung zu finden...");
            working.add("Ich versuche eine Entscheidung durch einen W" + random.nextInt(100) + " zu erzwingen...");
            working.add("Ich überlege ob Schnick-Schnack-Schnuck eine gute Lösung wäre...");
            working.add("Ich erklimme den Spiele-Olymp um die " + TeamManager.getInstance().getRandomAdjective() + " Spielegötter um Rat zu fragen.");
            working.add("Ich erbitte um Ratschläge bei den Ältesten der " + TeamManager.getInstance().getRandomRace() + "...");
            Game.randomForPlayers(mentionedMembers.size()).ifPresent(game -> working.add("Hättet ihr nicht einfach eine Partie " +  game.getName() + " spielen können anstatt mich zu fragen?"));
            Game.randomForPlayers(mentionedMembers.size()).ifPresent(game -> working.add("Damals hat man sowas noch mit einer Partie" +  game.getName() + " geklärt anstatt nen Bot damit zu belästigen..."));
            if(mentionedMembers.size() == 2) {
                working.add("War ja wieder klar, dass " + getUserName(mentionedMembers.get(0)) + " und " + getUserName(mentionedMembers.get(1)) + " sich nicht einigen können...");
                working.add("Hätten Bots mehr Rechte, dann würde ich ja einfach sagen \"Wenn 2 sich streiten, dann freut sich der dritte!\" Aber in diesem Fall bedeutet es nur Arbeit für mich...");
                working.add("Also " + getUserName(mentionedMembers.get(0)) + " gibt " + getUserName(mentionedMembers.get(1)) + " die Schlossallee zurück und dafür zerreißt " + getUserName(mentionedMembers.get(1)) + " keine 1000 DM Scheine mehr! Oh, falsches Spiel. Dann brauch ich einen kleinen Moment...");
            }
            working.add("Echt jetzt? Ihr seid " + mentionedMembers.size() + " Leute und schafft es nicht euch zu einigen???");
            working.add("Ach Mensch... Ihr seid mir echt ein Haufen aus " + TeamManager.getInstance().getRandomAdjective() + " Gamern... Aber ich schau mal nach ner passenden Subrutine für euch.... Ganz unten...");
            working.add("Klar! " + mentionedMembers.size() + " Spieler kommen nicht weiter und dann darf wieder der Bot einspringen und für die Gruppe von " + TeamManager.getInstance().getRandomAdjective() + " Spielern entscheiden..");
            working.add("Schonmal drüber nachgedacht das einfach unter euch auszumachen? Auch warum auch, der Botti kann das ja ausbügeln...");


            final List<String> sleeping = new ArrayList<>();
            sleeping.add("Gib mir einen kleinen Moment.");
            sleeping.add("Dann lass ich mal meine Schaltkreise warm laufen. Moment!");
            sleeping.add("Sorry, dauert nen Moment. Bin aber sicher trotzdem deutlich schneller als ihr.");

            channel.sendMessage(sleeping.get(random.nextInt(sleeping.size()))).complete();
            channel.sendTyping().complete();
            Executors.newSingleThreadExecutor().submit(() -> {
                try {
                    Thread.sleep(random.nextInt(MAX_SLEEP_TIME));
                    final int messageIndex = random.nextInt(working.size());
                    channel.sendMessage(working.get(messageIndex)).complete();
                    working.remove(messageIndex);
                    channel.sendTyping().complete();
                    int counter = 0;
                    while (random.nextBoolean() && counter < 4) {
                        Thread.sleep(random.nextInt(MAX_SLEEP_TIME));
                        final List<String> again = new ArrayList<>();
                        again.add("Nein, das hat leider nicht geholfen... Dann halt noch einmal von Vorne...");
                        again.add("Und noch einmal das ganze. Hab sonst nichts zu tun...");
                        again.add("Bei allen " + TeamManager.getInstance().getRandomAdjective() + " " + TeamManager.getInstance().getRandomRace() + " - Das hat leider nicht funktioniert. Ok, ich versuch mal was anderes...");
                        again.add("Wär ja auch zu schön gewesen, wenn das geklappt hätte. Dann probieren wir mal was anderes.");
                        again.add("Dann versuche ich es noch einmal. Bin ja zum Glück nur ein Bot und hab kein Privatleben das durch solche **WICHTIGEN** Endscheidungen gestört werden könnte...");
                        channel.sendMessage(again.get(random.nextInt(again.size()))).complete();
                        channel.sendTyping().complete();
                        Thread.sleep(random.nextInt(MAX_SLEEP_TIME));
                        channel.sendMessage(working.get(messageIndex)).complete();
                        working.remove(messageIndex);
                        counter++;
                    }
                    Thread.sleep(random.nextInt(MAX_SLEEP_TIME));
                    final Member winner = mentionedMembers.get(random.nextInt(mentionedMembers.size()));
                    final List<String> finalWinner = new ArrayList<>();
                    finalWinner.add("Verneigt euch vor **" + getUserName(winner) + "** - Der erste User, der es nicht schafft, eine Spiel ohne Unentschieden zu \"gewinnen\". Aber ich hab nen guten Tag und erkläre ihn mal zum \"Sieger\"...");
                    finalWinner.add("In all meiner Weisheit habe ich entschieden, dass **" + getUserName(winner) + "** dieses eine mal aus Mittleid von mir bevorzugt wird.");
                    finalWinner.add("**" + getUserName(winner) + "** das hast du ganz Toll gemacht! Am Ende durch die Zufallentscheidung eines Bots gewonnen! Wow! WIe ich dich kenne wird jetzt erst einmal allen erzählt wie toll du warst.");
                    finalWinner.add("Nicht das **" + getUserName(winner) + "** sich gleich wieder selbst voll abfeiert, aber leider muss ich mitteilen, dass ich als unparteiischer Bot diesen User zum Gewinner erkläre.");
                    finalWinner.add("Mein Kniffel-Co-Prozessor hat erwürfelt, dass **" + getUserName(winner) + "** eigentlich garnicht besser war aber ausnahmsweise in diesem Fall gewonnen hat.");
                    channel.sendMessage(finalWinner.get(random.nextInt(finalWinner.size()))).complete();
                } catch (final Exception e) {
                    throw new RuntimeException(e);
                }
            });

        }
    }

    @Override
    public String getHelp() {
        return "Ich kann dir helfen einen Gleichstand aufzulösen";
    }
}
