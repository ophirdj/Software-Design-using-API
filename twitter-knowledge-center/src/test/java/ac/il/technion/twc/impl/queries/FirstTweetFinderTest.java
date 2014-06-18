package ac.il.technion.twc.impl.queries;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import ac.il.technion.twc.api.Tweet;
import ac.il.technion.twc.api.TwitterQueryAPI;

/**
 * Test class for {@link FirstTweetFinder}.
 * 
 * @author Ophir De Jager
 * 
 */
public class FirstTweetFinderTest {

	private final FirstTweetFinder $ = new FirstTweetFinder();
	private final TwitterQueryAPI api = mock(TwitterQueryAPI.class);

	/**
	 * Initialize query with tweets.
	 */
	@Before
	public void setup() {
		when(api.getTweets()).thenReturn(
				Arrays.asList(new Tweet("base 1", null, new Date(1000), null,
						"Ophir"), new Tweet("base 2", null, new Date(2000),
						null, "Ziv"), new Tweet("re: base 2", "base 2",
						new Date(3000), null, "Ophir"), new Tweet("re: base 1",
						"base 1", new Date(4000), null, "Ziv"), new Tweet(
						"re: some tweet", "some tweet", new Date(1000), null,
						"Ziv")));
		$.visit(api);
		verify(api).getTweets();
	}

	/**
	 * Test method for {@link FirstTweetFinder#getUserFirstTweet(String)}.
	 */
	@Test
	public final void firstTweetShouldReturnFirstTweet() {
		assertEquals("base 1", $.getUserFirstTweet("Ophir"));
	}

	/**
	 * Test method for {@link FirstTweetFinder#getUserFirstTweet(String)}.
	 */
	@Test
	public final void firstTweetShouldReturnFirstTweetEvenIfRetweet() {
		assertEquals("re: some tweet", $.getUserFirstTweet("Ziv"));
	}

	/**
	 * Test method for {@link FirstTweetFinder#getUserFirstTweet(String)}.
	 */
	@Test(expected = FirstTweetFinder.NotFoundException.class)
	public final void firstTweetOfUserThatDoesntExistShouldThrow() {
		$.getUserFirstTweet("I am not a user");
	}

	/**
	 * Test method for {@link FirstTweetFinder#clearData()}.
	 */
	@Test(expected = FirstTweetFinder.NotFoundException.class)
	public final void clearDataShouldClearAllTweets() {
		$.clearData();
		$.getUserFirstTweet("Ophir");
	}

}
