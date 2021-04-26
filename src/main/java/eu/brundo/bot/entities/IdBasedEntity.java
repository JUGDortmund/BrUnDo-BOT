package eu.brundo.bot.entities;

import org.bson.types.ObjectId;

public interface IdBasedEntity {

    ObjectId getId();

    void setId(final ObjectId id);

}
