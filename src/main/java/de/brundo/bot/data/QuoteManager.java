package de.brundo.bot.data;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.io.Resources;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;


/**
 * Manages the collection of quotes saved as json files. Provides multiple methods for accessing quotes.
 */
public class QuoteManager {
	
	private final static Logger LOG = LoggerFactory.getLogger(QuoteManager.class);
	
	private final static QuoteManager INSTANCE = new QuoteManager();
	private final static String RESOURCE = "/quotes.json";
	private final Random random;
	
	private List<Quote> quotes = null;
	
	/**
	 * Constructor for QuoteManager class. This will try to open the resource file and deserialize all quotes to a {@link Arraylist} of {@link Quote}s.
	 */
	public QuoteManager() {
		String jsonString = "";
		random = new Random(System.currentTimeMillis());
		try {
			// Read the quotes from the json file and deserialize them to a colleection of quotes.
			jsonString = Resources.toString(TeamManager.class.getResource(RESOURCE), StandardCharsets.UTF_8);
			Gson gson = new Gson();
			@SuppressWarnings("serial")
			Type quoteType = new TypeToken<ArrayList<Quote>>(){}.getType();
			quotes = gson.fromJson(jsonString, quoteType);
		} catch (IOException ex) {
			LOG.error(String.format("Fehler beim Öffnen der Resourcedatei %s", RESOURCE), ex);
		} catch (JsonSyntaxException ex) {
			LOG.error(String.format("Fehler beim deserialisieren des String %s", jsonString), ex);
		}
	}
	
	/**
	 * Returns a random {@link Quote} from the complete collection of quotes.
	 * @return A {@link Quote}, null if initialization failed.
	 */
	public Quote getRandomQuote() {
		if (quotes != null) {
			return quotes.get(random.nextInt(quotes.size()));
		}
		LOG.debug("No quotes found!");
		return null;
	}
	
	public static QuoteManager getInstance() {
		return INSTANCE;
	}
}
