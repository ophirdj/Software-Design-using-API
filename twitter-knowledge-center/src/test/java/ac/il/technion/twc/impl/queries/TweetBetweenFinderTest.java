package ac.il.technion.twc.impl.queries;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import ac.il.technion.twc.api.Tweet;
import ac.il.technion.twc.api.TwitterQueryAPI;
import ac.il.technion.twc.impl.queries.TweetBetweenFinder.NotTweetInRangeException;

/**
 * Test class for {@link TweetBetweenFinder}.
 * 
 * @author Ophir De Jager
 * 
 */
public class TweetBetweenFinderTest {

	private final TweetBetweenFinder $ = new TweetBetweenFinder();

	/**
	 * Initialize query data.
	 */
	@Before
	public void setup() {
		final List<Tweet> tweets = new ArrayList<>();
		for (long time = 1000L; time <= 10000L; time += 1000L)
			tweets.add(new Tweet(Long.toString(time), null, new Date(time),
					null, null));
		final TwitterQueryAPI twitter = mock(TwitterQueryAPI.class);
		when(twitter.getTweets()).thenReturn(tweets);
		$.visit(twitter);
	}

	/**
	 * Test method for {@link TweetBetweenFinder#findTweetBetween(Date, Date)}.
	 * 
	 * @throws NotTweetInRangeException
	 */
	@Test
	public final void findTweetBetweenTweetExistsShouldReturnTweet()
			throws NotTweetInRangeException {
		assertEquals("2000", $.findTweetBetween(new Date(1500), new Date(2100)));
	}

	/**
	 * Test method for {@link TweetBetweenFinder#findTweetBetween(Date, Date)}.
	 * 
	 * @throws NotTweetInRangeException
	 */
	@Test(expected = NotTweetInRangeException.class)
	public final void findTweetBetweenTweetDoesNotExistShouldThrow()
			throws NotTweetInRangeException {
		$.findTweetBetween(new Date(1500), new Date(1600));
	}

	/**
	 * Test method for {@link TweetBetweenFinder#findTweetBetween(Date, Date)}.
	 * 
	 * @throws NotTweetInRangeException
	 */
	@Test
	public final void findTweetBetweenTweetWithTimeAsLowerBoundShouldReturnTweet()
			throws NotTweetInRangeException {
		assertEquals("1000", $.findTweetBetween(new Date(1000), new Date(1600)));
	}

	/**
	 * Test method for {@link TweetBetweenFinder#findTweetBetween(Date, Date)}.
	 * 
	 * @throws NotTweetInRangeException
	 */
	@Test
	public final void findTweetBetweenTweetWithTimeAsUpperBoundShouldReturnTweet()
			throws NotTweetInRangeException {
		assertEquals("2000", $.findTweetBetween(new Date(1500), new Date(2000)));
	}

	/**
	 * Test method for {@link TweetBetweenFinder#findTweetBetween(Date, Date)}.
	 * 
	 * @throws NotTweetInRangeException
	 */
	@Test(expected = NotTweetInRangeException.class)
	public final void findTweetBetweenAfterClearDataShouldThrow()
			throws NotTweetInRangeException {
		$.clearData();
		$.findTweetBetween(new Date(0), new Date(20000));
	}

}
