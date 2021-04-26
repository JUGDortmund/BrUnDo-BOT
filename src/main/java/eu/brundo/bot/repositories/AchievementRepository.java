package eu.brundo.bot.repositories;

import dev.morphia.query.experimental.filters.Filters;
import eu.brundo.bot.MongoConnector;
import eu.brundo.bot.entities.AchievementEntity;
import eu.brundo.bot.entities.MemberEntity;

import java.util.List;
import java.util.Objects;

public class AchievementRepository {

    private final MongoConnector mongoConnector;

    public AchievementRepository(final MongoConnector mongoConnector) {
        this.mongoConnector = Objects.requireNonNull(mongoConnector);
    }

    public AchievementEntity createEntity() {
        return new AchievementEntity();
    }

    public boolean hasAchived(final MemberEntity memberEntity, final String achievementId) {
        Objects.requireNonNull(memberEntity);
        Objects.requireNonNull(achievementId);
        return mongoConnector.getDatastore()
                .find(AchievementEntity.class)
                .filter(Filters.eq("member", memberEntity))
                .filter(Filters.eq("achievementId", achievementId))
                .count() != 0;
    }

    public List<AchievementEntity> getAllForMember(final MemberEntity memberEntity) {
        Objects.requireNonNull(memberEntity);
        return mongoConnector.getDatastore()
                .find(AchievementEntity.class)
                .filter(Filters.eq("member", memberEntity))
                .iterator()
                .toList();
    }

    public void save(final AchievementEntity entity) {
        Objects.requireNonNull(entity);
        if (entity.getId() == null) {
            mongoConnector.getDatastore().save(entity);
        } else {
            mongoConnector.getDatastore().merge(entity);
        }
    }
}
