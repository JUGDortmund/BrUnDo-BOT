package de.brundo.bot.data;

import org.apache.commons.lang3.StringUtils;

/**
 * Simple class to hold quotes for easy serialization and deserialization
 *
 */
public class Quote {

	private String group;
	private String quote;
	private String source;
	
	public Quote(String group, String quote) {
		this.group = group;
		this.quote = quote;
	}
	
	public Quote(String group, String quote, String source) {
		this.group = group;
		this.quote = quote;
		this.source = source;
	}
	
	/**
	 * Returns a formatted String of the quote, the group/category, and, if applicable, the source/author
	 * @return The formatted String
	 */
	public String toFormattedString() {
		if (StringUtils.isNotEmpty(this.source)) {
			return String.format("\"%s\", %s [Kategorie: %s]", this.quote, this.source, this.group);
		}
		return String.format("\"%s\" [Kategorie: %s]", this.quote, this.group);
	}
	
	public String getGroup() {
		return group;
	}
	public void setGroup(String group) {
		this.group = group;
	}
	public String getQuote() {
		return quote;
	}
	public void setQuote(String quote) {
		this.quote = quote;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	
}
