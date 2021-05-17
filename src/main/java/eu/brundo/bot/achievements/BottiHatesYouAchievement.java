package eu.brundo.bot.achievements;

import eu.brundo.bot.data.BadnessManager;
import eu.brundo.bot.util.BrundoUtils;
import net.dv8tion.jda.api.entities.Member;

public class BottiHatesYouAchievement extends AbstractCheckableAchievement {

    public BottiHatesYouAchievement() {
        super("BottiHatesYou", 10);
    }

    @Override
    public boolean achived(final Member member) {
        return BadnessManager.getInstance().getBadness(BrundoUtils.getUserName(member)) == 100;
    }
}
