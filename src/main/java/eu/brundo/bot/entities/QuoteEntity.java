package eu.brundo.bot.entities;

import org.bson.types.ObjectId;

import dev.morphia.annotations.Entity;
import dev.morphia.annotations.Id;
import dev.morphia.annotations.Reference;
import eu.brundo.bot.data.Quote;

@Entity("quoteEntity")
public class QuoteEntity implements IdBasedEntity, WithMemberRelation {

	public static final String MEMBER = "member";
	public static final String GROUP = "group";
	public static final String QUOTE = "quote";
	public static final String SOURCE = "source";
	
	@Id
    private ObjectId id;

    @Reference
    private MemberEntity member;
    
    private String group;
	private String quote;
	private String source;
	
	public QuoteEntity() {
		
	}
	
	public QuoteEntity(Quote quote) {
		this.group = quote.getGroup();
		this.quote = quote.getQuote();
		this.source = quote. getSource();
	}
	
	@Override
	public MemberEntity getMember() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setMember(MemberEntity member) {
		// TODO Auto-generated method stub

	}

	@Override
	public ObjectId getId() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setId(ObjectId id) {
		// TODO Auto-generated method stub

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
