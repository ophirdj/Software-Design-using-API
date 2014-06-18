package ac.il.technion.twc.impl.queries;

import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import ac.il.technion.twc.api.Tweet;
import ac.il.technion.twc.api.TwitterQueryAPI;
import ac.il.technion.twc.impl.queries.OriginFinder.NotFoundException;

/**
 * Tests for {@link OriginFinder}
 * 
 * @author Ziv Ronen
 * @date 07.05.2014
 * @mail akarks@gmail.com
 */
public class OriginFinderTest {

	/**
   * 
   */
	public final @Rule
	ExpectedException thrown = ExpectedException.none();

	private final OriginFinder $ = new OriginFinder();

	private static TwitterQueryAPI twitter(final List<Tweet> l) {
		final TwitterQueryAPI $ = mock(TwitterQueryAPI.class);
		when($.getTweets()).thenReturn(l);
		return $;
	}

	/**
	 * Test method for: {@link OriginFinder#origin(String)}.
	 * 
	 * @throws Exception
	 */
	@Test
	public void findRootOnRetweetAfterAddingOnlyTheRetweetShouldThrowNoRootFoundException()
			throws Exception {
		final Tweet rt = new Tweet("retweetId", "baseId", new Date(1L), null,
				null);
		thrown.expect(NotFoundException.class);
		$.visit(twitter(Arrays.asList(rt)));
		$.origin(rt.getTweetID());
	}

	/**
	 * Test method for: {@link OriginFinder#origin(String)}.
	 * 
	 * @throws Exception
	 */
	@Test
	public void findRootOnRetweetAfterAddingFirstTheOriginalTweetShouldReturnTheOriginalTweet()
			throws Exception {
		final Tweet bt = new Tweet("baseId", null, new Date(1L), null, null);
		final Tweet rt = new Tweet("retweetId", bt.getTweetID(), new Date(1L),
				null, null);
		$.visit(twitter(Arrays.asList(bt, rt)));
		assertSame(bt.getTweetID(), $.origin(rt.getTweetID()));
	}

	/**
	 * Test method for: {@link OriginFinder#origin(String)}.
	 * 
	 * @throws Exception
	 */
	@Test
	public void findRootOnRetweetAfterAddingFirstTheTheRetweetShouldReturnTheOriginalTweet()
			throws Exception {
		final Tweet bt = new Tweet("baseId", null, new Date(1L), null, null);
		final Tweet rt = new Tweet("retweetId", bt.getTweetID(), new Date(1L),
				null, null);
		$.visit(twitter(Arrays.asList(rt, bt)));
		assertSame(bt.getTweetID(), $.origin(rt.getTweetID()));
	}

	/**
	 * Test method for: {@link OriginFinder#origin(String)}.
	 * 
	 * @throws Exception
	 */
	@Test
	public void findRootOnRetweetWithUnrelatedBaseTweetShouldThrowNoRootFoundException()
			throws Exception {
		final Tweet bt = new Tweet("otherId", null, new Date(1L), null, null);
		final Tweet rt = new Tweet("retweetId", "baseId", new Date(1L), null,
				null);
		$.visit(twitter(Arrays.asList(rt, bt)));
		thrown.expect(NotFoundException.class);
		$.origin(rt.getTweetID());
	}

	/**
	 * Test method for: {@link OriginFinder#origin(String)}.
	 * 
	 * @throws Exception
	 */
	@Test
	public void findRootOnRetweetOfRetweetShouldReturnTheBaseTweet()
			throws Exception {
		final Tweet bt = new Tweet("baseId", null, new Date(1L), null, null);
		final Tweet rt1 = new Tweet("retweetId", bt.getTweetID(), new Date(1L),
				null, null);
		final Tweet rt2 = new Tweet("retweetId", bt.getTweetID(), new Date(1L),
				null, null);
		$.visit(twitter(Arrays.asList(rt1, bt, rt2)));
		assertSame(bt.getTweetID(), $.origin(rt2.getTweetID()));
	}

	/**
	 * Test method for: {@link OriginFinder#clearData()}.
	 * 
	 * @throws Exception
	 */
	@Test
	public void findRootAfterClearDataShouldThrowNoRootFoundException()
			throws Exception {
		final Tweet t = new Tweet("id", null, new Date(1L), null, null);
		$.visit(twitter(Arrays.asList(t)));
		$.clearData();
		thrown.expect(NotFoundException.class);
		$.origin(t.getTweetID());
	}

}
