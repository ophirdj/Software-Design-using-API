package ac.il.technion.twc.impl.queries;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import ac.il.technion.twc.api.Tweet;
import ac.il.technion.twc.api.TwitterQueryAPI;

/**
 * Test class for {@link HashtagsHighestCouplingFinder}.
 * 
 * @author Ophir De Jager
 * 
 */
public class HashtagsHighestCouplingFinderTest {

	private static String HASHTAGS[][] = new String[][] { { "1a", "1b" },
			{ "2a", "2b" }, { "3a", "3b" }, { "4a", "4b" }, { "5a", "5b" } };

	private static String COUPLES[] = format(HASHTAGS);

	private final HashtagsHighestCouplingFinder $ = new HashtagsHighestCouplingFinder();

	/**
	 * Initialize query with tweets.
	 */
	@Before
	public void setup() {
		final TwitterQueryAPI twitter = mock(TwitterQueryAPI.class);
		when(twitter.getTweets()).thenReturn(tweets());
		$.visit(twitter);
	}

	private static List<Tweet> tweets() {
		final List<Tweet> $ = new ArrayList<>();
		for (int i = 0; i < HASHTAGS.length; ++i)
			for (int j = 0; j <= i; ++j) {
				final int tweetNum = i * HASHTAGS.length + j;
				$.add(new Tweet("base " + tweetNum, null, new Date(tweetNum),
						Arrays.asList(HASHTAGS[j]), null));
			}
		Collections.shuffle($);
		return $;
	}

	private static List<Tweet> noise() {
		final List<Tweet> $ = new ArrayList<>();
		for (int i = 0; i < HASHTAGS.length; ++i)
			$.add(new Tweet("retweet " + i, "some tweet", new Date(i), Arrays
					.asList(HASHTAGS[i]), null));
		for (int i = 0; i < HASHTAGS.length; ++i)
			$.add(new Tweet("noise null hashtags" + i, null, new Date(i), null,
					null));
		for (int i = 0; i < HASHTAGS.length; ++i)
			$.add(new Tweet("noise empty hashtags" + i, null, new Date(i),
					Collections.<String> emptyList(), null));
		Collections.shuffle($);
		return $;
	}

	private static String[] format(final String hashtags[][]) {
		final String $[] = new String[hashtags.length];
		for (int i = 0; i < $.length; ++i) {
			final String s = hashtags[i][0];
			final String t = hashtags[i][1];
			$[i] = s.compareTo(t) < 0 ? s + "," + t : t + "," + s;
		}
		return $;
	}

	/**
	 * Test method for {@link HashtagsHighestCouplingFinder#clearData()}.
	 */
	@Test
	public final void clearDataShouldReturn0Couples() {
		$.clearData();
		assertEquals(0, $.kMostCoupled(1).length);
	}

	/**
	 * Test method for {@link HashtagsHighestCouplingFinder#kMostCoupled(int)}.
	 */
	@Test
	public final void kMostCoupledShouldReturnAtMostKCouples() {
		final int k = 2;
		assertTrue(k >= $.kMostCoupled(k).length);
	}

	/**
	 * Test method for {@link HashtagsHighestCouplingFinder#kMostCoupled(int)}.
	 */
	@Test
	public final void kMostCoupledShouldReturnNoMoreCouplesThanThereAre() {
		final int k = HASHTAGS.length + 10;
		assertTrue(HASHTAGS.length >= $.kMostCoupled(k).length);
	}

	/**
	 * Test method for {@link HashtagsHighestCouplingFinder#kMostCoupled(int)}.
	 */
	@Test
	public final void kMostCoupledShouldReturnCouplesInCorrectOrder() {
		final int k = HASHTAGS.length;
		final String couples[] = $.kMostCoupled(k);
		assertArrayEquals(COUPLES, couples);
	}

	/**
	 * Test method for {@link HashtagsHighestCouplingFinder#kMostCoupled(int)}.
	 */
	@Test
	public final void kMostCoupledShouldNotBeAffectedByRetweetsShouldReturnCouplesInCorrectOrder() {
		final TwitterQueryAPI twitter = mock(TwitterQueryAPI.class);
		final List<Tweet> tweetsWithNoise = tweets();
		tweetsWithNoise.addAll(noise());
		Collections.shuffle(tweetsWithNoise);
		when(twitter.getTweets()).thenReturn(tweetsWithNoise);
		$.visit(twitter);
		final int k = HASHTAGS.length;
		final String couples[] = $.kMostCoupled(k);
		assertArrayEquals(COUPLES, couples);
	}

	/**
	 * Test method for {@link HashtagsHighestCouplingFinder#kMostCoupled(int)}.
	 */
	@Test
	public void kMostCoupledShouldReturnHashtagsWithHighestCouplingEvenWhenThereAreMoreThan2HashtagsInTweet() {
		final TwitterQueryAPI twitter = mock(TwitterQueryAPI.class);
		when(twitter.getTweets()).thenReturn(
				Arrays.asList(
						new Tweet("id1", null, new Date(1000), Arrays.asList(
								"1a", "1b", "2b"), null), new Tweet("id1",
								null, new Date(1000),
								Arrays.asList("1a", "2b"), null)));
		$.visit(twitter);
		assertArrayEquals(new String[] { "1a,2b" }, $.kMostCoupled(1));
	}
}
