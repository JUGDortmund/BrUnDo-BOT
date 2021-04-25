package eu.brundo.bot.repositories;

import eu.brundo.bot.MongoConnector;
import eu.brundo.bot.entities.ChannelSessionEntity;

import java.util.Objects;

public class ChannelSessionRepository {

    private final MongoConnector mongoConnector;

    public ChannelSessionRepository(final MongoConnector mongoConnector) {
        this.mongoConnector = Objects.requireNonNull(mongoConnector);
    }

    public ChannelSessionEntity createEntity() {
        return new ChannelSessionEntity();
    }

    public void save(final ChannelSessionEntity entity) {
        Objects.requireNonNull(entity);
        if (entity.getId() == null) {
            mongoConnector.getDatastore().save(entity);
        } else {
            mongoConnector.getDatastore().merge(entity);
        }
    }
}
