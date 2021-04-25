package eu.brundo.bot;

public interface ApplicationEnvironment {

    String DISCORD_APP_TOKEN_SYS_VAR = "DISCORD_TOKEN";

    String MONGO_CONNECTION_SYS_VAR = "MONGO_CONNECTION";

    public static String getDiscordToken() {
        return System.getenv(DISCORD_APP_TOKEN_SYS_VAR);
    }

    public static String getMongoConnection() {
        return System.getenv(MONGO_CONNECTION_SYS_VAR);
    }
}
