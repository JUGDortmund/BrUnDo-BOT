package eu.brundo.bot.repositories;

import dev.morphia.query.experimental.filters.Filters;
import eu.brundo.bot.MongoConnector;
import eu.brundo.bot.entities.ChannelSessionEntity;
import eu.brundo.bot.entities.MemberEntity;

import java.util.List;
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

    public List<ChannelSessionEntity> findAllForMember(final MemberEntity memberEntity) {
        return mongoConnector.getDatastore()
                .find(ChannelSessionEntity.class)
                .filter(Filters.eq("member", memberEntity))
                .iterator()
                .toList();
    }
}
