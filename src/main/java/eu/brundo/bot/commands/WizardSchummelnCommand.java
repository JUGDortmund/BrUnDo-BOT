package eu.brundo.bot.commands;

import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class WizardSchummelnCommand extends AbstractCommand {

    public WizardSchummelnCommand() {
        super("werSchummeltBeiWizard");
    }

    @Override
    protected void onCommand(final MessageReceivedEvent event) {
        final MessageChannel channel = event.getChannel();

        final List<String> answers = new ArrayList<>();
        answers.add("Also die MaSchiPfen spielt auf jeden Fall in der Regel mit 2 Accounts pro Partie!");
        answers.add("Also der DezimalRenter und die Kumi Ori haben geheime Zeichen ausgemacht und spielen immer zusammen gegen alle anderen!");
        answers.add("Also der Snegi unterhält sich immer heimlich mit dem Newborger und tauscht Kartengeheimnisse aus!");
        answers.add("Alleine gestern abend haben alle außer Hendrik die übelsten Tricks ausgepackt!");
        answers.add("Man munkelt Mareike hat immer 2 komplette Kartensets in der Bluse versteckt!");
        answers.add("Newborger lenkt immer alle mit seinem ach-so-tollen Rang ab. Und wenn man dann nach unten zur Liste scrollt löscht er einfach wild Karten!");
        answers.add("Die Birgit hat der Michelle verboten mitzuspielen, damit die in der Zeit alle anderen Spieler ausspionieren kann");
        answers.add("Das kann ich dir nicht sagen aber der Kolja meint, dass BoardgamePunk der einzig ehrliche Spieler am Tisch ist.");
        answers.add("Glaubt ihr etwa der Newborger ist auf die Rangliste gekommen, weil er so gut spielen kann? o__O");
        answers.add("Das ist wieder so ein Menschen-Ding oder? Woher soll ich das bitte wissen?");

        final Random random = new Random(System.currentTimeMillis());
        sendTranslatedMessage(channel, getRandomEntry(answers));
    }

    @Override
    public String getHelp() {
        return "Die Wahrheit, nichts als die pure Wahrheit";
    }

    @Override
    public boolean listInHelp() {
        return false;
    }

    @Override
    public CommandCategories getCategory() {
        return CommandCategories.ADDITIONAL_CATEGORY;
    }
}
