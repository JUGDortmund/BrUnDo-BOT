package eu.brundo.bot.commands;

import eu.brundo.bot.data.QuoteManager;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

/**
 * The QuoteCommand class provides a !quote method to get multiple random quotes. It can be used to specify a specific type of quotes, e.g. !quote anhalter to only use quotes from the Hitchhiker's Guide to the Galaxy
 */
public class QuoteCommand extends AbstractCommand {

    public QuoteCommand() {
        super("quote");
    }

    @Override
    protected void onCommand(final MessageReceivedEvent event) {
        final MessageChannel channel = event.getChannel();
        channel.sendMessage(String.format("Hier ist ein zufälliges Zitat für dich:\n%s", QuoteManager.getInstance().getRandomQuote().toFormattedString())).queue();
    }

    @Override
    public String getHelp() {
        return "Ein zufälliges Zitat ausgeben. Kann auch verwendet werden um spezifischere Zitate auszugeben.";
    }

    @Override
    public CommandCategories getCategory() {
        return CommandCategories.ADDITIONAL_CATEGORY;
    }

}
