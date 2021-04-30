package eu.brundo.bot.commands;

import java.util.Objects;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import eu.brundo.bot.MongoConnector;
import eu.brundo.bot.data.Quote;
import eu.brundo.bot.data.QuoteManager;
import eu.brundo.bot.entities.QuoteEntity;
import eu.brundo.bot.services.QuoteService;
import eu.brundo.bot.util.BottiResourceBundle;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

/**
 * The QuoteCommand class provides a !quote method to get multiple random quotes. It can be used to specify a specific type of quotes, e.g. !quote anhalter to only use quotes from the Hitchhiker's Guide to the Galaxy
 */
public class QuoteCommand extends AbstractCommand {

	private final static Logger LOG = LoggerFactory.getLogger(QuoteCommand.class);
	
	private static final String RETURN_RANDOM_QUOTE = "command.quote.returnRandomQuote";
	private static final String HELP = "command.quote.help";
	private static final String HELP_ADD_QUOTE = "command.quote.helpAddQuote";
	private static final String ERROR_PARSE_QUOTE = "command.quote.errorParseQuote";
	private static final String COMMAND_ADD = "add";
	private static final String SEPARATOR_CHAR = " ";
	
	private static final int MAX_TOKENS = 3;
	private static final int COMMAND_POSITION = 1;
	private static final int QUOTE_POSITION = 2;
	
	private final QuoteService quoteService;
	
	public QuoteCommand(final MongoConnector mongoConnector) {
		super("quote");
		quoteService = new QuoteService(mongoConnector);
	}
	
	@Override
	protected void onCommand(MessageReceivedEvent event) {
		final MessageChannel channel = event.getChannel();
		final Member member = event.getMember();
		final String message = event.getMessage().getContentDisplay();
		if (message.length() == "!quote".length()) {
			sendMessage(channel, RETURN_RANDOM_QUOTE, member.getUser().getName(), quoteService.getRandomQuote().toFormattedString());
		} 
		final String command = getCommand(message);
		switch(Objects.requireNonNull(command)) {
		case COMMAND_ADD:
			addQuote(message, channel, member);
		}
		
	}

	@Override
	public String getHelp() {
		return BottiResourceBundle.getMessage(HELP);
	}
	
	private void addQuote(String message, MessageChannel channel, Member member) {
		String[] tokens = getTokensFromCommand(message);
		if (tokens.length < 3) {
			sendMessage(channel, HELP_ADD_QUOTE, "{", "}");
		} else {
			Quote quote = parseQuote(tokens[QUOTE_POSITION]);
			if (quote != null) {
				quoteService.getOrCreateQuoteEntity(quote, member);
			} else {
				sendMessage(channel, ERROR_PARSE_QUOTE, "{", "}");
			}
		}
	}
	
	@Override
    public CommandCategories getCategory() {
        return CommandCategories.ADDITIONAL_CATEGORY;
    }
	
	private Quote parseQuote(String quoteString) {
		try {
			Gson gson = new Gson();
		    return gson.fromJson(quoteString, Quote.class);
		} catch (JsonSyntaxException ex) {
			LOG.error(String.format("Json-Error while trying to parse the String %s", quoteString), ex);
		}
		return null;
	}
	
	private String getCommand(String message) {
		String[] tokens = getTokensFromCommand(message);
		if (tokens.length > 1) {
			return tokens[COMMAND_POSITION];
		}
		return null;
	}
	
	private String[] getTokensFromCommand(String message) {
		return StringUtils.split(message, SEPARATOR_CHAR, MAX_TOKENS);
	}

    

}
