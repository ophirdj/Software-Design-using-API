package ac.il.technion.twc.impl.queries;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import ac.il.technion.twc.api.Tweet;
import ac.il.technion.twc.api.TwitterQueryAPI;

public class RetweetCounterTest {

	private final RetweetCounter $ = new RetweetCounter();

	/**
	 * Initialization.
	 */
	@Before
	public void setup() {
		final List<Tweet> tweets = Arrays.asList(new Tweet("base 1", null,
				new Date(1000), null, null), new Tweet("base 2", null,
				new Date(2000), null, null), new Tweet("re: base 2", "base 2",
				new Date(3000), null, null), new Tweet("re: base 1", "base 1",
				new Date(4000), null, null), new Tweet("re: re: base 1",
				"re: base 1", new Date(5000), null, null), new Tweet(
				"re: some tweet", "some tweet", new Date(1000), null, null));
		final OriginFinder f = new OriginFinder();
		final TwitterQueryAPI twitter = mock(TwitterQueryAPI.class);
		when(twitter.getTweets()).thenReturn(tweets).thenReturn(tweets);
		f.visit(twitter);
		$.visit(twitter);
		$.init(f);
	}

	@Test
	public final void counterCountsNumberOfRetweets() {
		assertEquals(3, $.getRetweetCount("base 1"));
	}

}
