package eu.brundo.bot.tasks;

import eu.brundo.bot.MongoConnector;
import net.dv8tion.jda.api.JDA;

public abstract class AbstractTask implements Runnable {

    private final JDA jda;

    private final MongoConnector mongoConnector;

    protected AbstractTask(final JDA jda, final MongoConnector mongoConnector) {
        this.jda = jda;
        this.mongoConnector = mongoConnector;
    }

    protected void sleep(final long ms) {
        try {
            Thread.sleep(ms);
        } catch (final Exception e) {
            throw new RuntimeException("Sleep interrupted!", e);
        }
    }

    protected JDA getJda() {
        return jda;
    }

    protected MongoConnector getMongoConnector() {
        return mongoConnector;
    }
}
