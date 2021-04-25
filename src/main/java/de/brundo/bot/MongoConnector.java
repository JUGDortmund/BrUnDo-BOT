package de.brundo.bot;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import dev.morphia.Datastore;
import dev.morphia.Morphia;

public class MongoConnector {

    private final Datastore datastore;

    public MongoConnector() {
        final MongoClient mongoClient = MongoClients.create(ApplicationEnvironment.getMongoConnection());
        datastore = Morphia.createDatastore(mongoClient, "botti_database");
        datastore.getMapper().mapPackage("de.brundo.bot.store");
        datastore.ensureIndexes();
    }

    public Datastore getDatastore() {
        return datastore;
    }
}
