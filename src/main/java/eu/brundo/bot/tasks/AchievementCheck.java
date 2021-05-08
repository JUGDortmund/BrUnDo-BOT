package eu.brundo.bot.tasks;

import eu.brundo.bot.MongoConnector;
import eu.brundo.bot.services.AchievementService;
import eu.brundo.bot.services.MemberService;
import eu.brundo.bot.util.TimeUtils;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Member;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class AchievementCheck extends AbstractTask {

    private final static Logger LOG = LoggerFactory.getLogger(AchievementCheck.class);

    private final MemberService memberService;

    private final AchievementService achievementService;

    public AchievementCheck(final JDA jda, final MongoConnector mongoConnector) {
        super(jda, mongoConnector);
        memberService = new MemberService(mongoConnector);
        achievementService = new AchievementService(mongoConnector);
    }

    @Override
    public void run() {
        final List<Member> knownMembers = memberService.getAllMembers(getJda());
        achievementService.checkAll(knownMembers);
        LOG.info("Servertime: {}", TimeUtils.nowInGermany());
        sleep(30_000);
    }
}
