package eu.brundo.bot.repositories;

import static eu.brundo.bot.entities.QuoteEntity.GROUP;
import static eu.brundo.bot.entities.QuoteEntity.SOURCE;
import static eu.brundo.bot.entities.QuoteEntity.MEMBER;
import java.util.List;
import java.util.Objects;

import dev.morphia.query.experimental.filters.Filters;
import eu.brundo.bot.MongoConnector;
import eu.brundo.bot.entities.QuoteEntity;
import net.dv8tion.jda.api.entities.Member;

public class QuoteRepository {

	private final MongoConnector mongoConnector;
	
	public QuoteRepository (final MongoConnector mongoConnector) {
		this.mongoConnector = Objects.requireNonNull(mongoConnector);
	}
	
	public QuoteEntity createEntity() {
		return new QuoteEntity();
	}
	
	public void save (final QuoteEntity entity) {
		Objects.requireNonNull(entity);
		if (entity.getId() == null) {
            mongoConnector.getDatastore().save(entity);
        } else {
            mongoConnector.getDatastore().merge(entity);
        }
	}
	
	public List<QuoteEntity> getAll() {
        return mongoConnector.getDatastore()
                .find(QuoteEntity.class)
                .iterator()
                .toList();
    }
	
	public List<QuoteEntity> getGroup(String group) {
		return mongoConnector
				.getDatastore()
				.find(QuoteEntity.class)
				.filter(Filters.eq(GROUP, group))
				.iterator()
				.toList();
	}
	
	public List<QuoteEntity> getSource(String source) {
		return mongoConnector
				.getDatastore()
				.find(QuoteEntity.class)
				.filter(Filters.eq(SOURCE, source))
				.iterator()
				.toList();
	}
	
	public List<QuoteEntity> getMember(Member member) {
		return mongoConnector
				.getDatastore()
				.find(QuoteEntity.class)
				.filter(Filters.eq(MEMBER, member))
				.iterator()
				.toList();
	}
}
