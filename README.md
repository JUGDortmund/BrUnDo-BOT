# BrUnDo-BOT

Java basierter BOT für den Discord Server der [Dortmunder Brettspielunion](https://brettspielunion-dortmund.de).

Der BOT ist als Maven Projekt erstellt und kann somit mit jeder Java IDE genutzt werden. Der BOT benötigt Java 11 und es
wird empfohlen die Distribution von [AdoptOpenJDK](https://adoptopenjdk.net) zu nutzen.

## Integration in Discord

Im [Discord Developer Portal](https://discord.com/developers/) ist für die BrUnDo ein Team hinterlegt über den der BOT
innerhalb von Discord verwaltet wird.

## Persistenzschicht

Der BOT speichert Daten in einer MongoDB-Instanz, welche direkt in der [MongoDB Cloud](https://cloud.mongodb.com)
gehostet ist. [Morphia](https://morphia.dev/morphia/2.1/index.html) wird als ER Mapper für den Zugriff auf die DB
genutzt.

## Setup

Um den BOT laufen zu lassen müssen 2 Umgebungsvariablen genutzt werden:

- `DISCORD_TOKEN` muss den Token aus dem Discord Developer Portal beinhalten
- `MONGO_CONNECTION` muss den Verbindungs-String zur Mongo-DB beinhalten


