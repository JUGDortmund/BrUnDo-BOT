package eu.brundo.bot.services;

import java.util.List;
import java.util.Random;

import eu.brundo.bot.MongoConnector;
import eu.brundo.bot.data.Quote;
import eu.brundo.bot.entities.MemberEntity;
import eu.brundo.bot.entities.QuoteEntity;
import eu.brundo.bot.repositories.MemberRepository;
import eu.brundo.bot.repositories.QuoteRepository;
import net.dv8tion.jda.api.entities.Member;

public class QuoteService {

	private final QuoteRepository quoteRepository;
	private final MemberRepository memberRepository;
	
	public QuoteService(final MongoConnector mongoConnector) {
		this.quoteRepository = new QuoteRepository(mongoConnector);
		this.memberRepository = new MemberRepository(mongoConnector);
	}
	
	public QuoteEntity getOrCreateQuoteEntity(Quote quote, Member member) {
		final MemberEntity memberEntity = memberRepository.findMemberByDiscordId(member.getId()).orElseGet(() -> {
            final MemberEntity entity1 = memberRepository.createNewEntity(member);
            memberRepository.save(entity1);
            return entity1;
        });
		QuoteEntity quoteEntity = new QuoteEntity(quote);
		quoteEntity.setMember(memberEntity);
		quoteRepository.save(quoteEntity);
		return quoteEntity;
	}
	
	public Quote getRandomQuote() {
		Random random = new Random(System.currentTimeMillis());
		List<QuoteEntity> quotes = quoteRepository.getAll();
		return new Quote(quotes.get(random.nextInt(quotes.size())));
	}
}
