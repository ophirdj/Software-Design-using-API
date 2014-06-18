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
 * Test class for {@link UserTweetsQueries}.
 * 
 * @author Ophir De Jager
 * 
 */
public class UserTweetsQueriesTest {

	private final UserTweetsQueries $ = new UserTweetsQueries();

	/**
	 * Initialize query with tweets.
	 */
	@Before
	public void setup() {
		final TwitterQueryAPI api = mock(TwitterQueryAPI.class);
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
	 * Tests method for {@link UserTweetsQueries#getTweetsCountOfUser(String)}.
	 */
	@Test
	public final void getTweetsCountOfUserShouldReturnNumberOfTweetsUserHas() {
		assertEquals(2, $.getTweetsCountOfUser("Ophir"));
	}

	/**
	 * Tests method for {@link UserTweetsQueries#getTweetsCountOfUser(String)}.
	 */
	@Test
	public final void getTweetsCountOfNonExistingUserShouldReturn0() {
		assertEquals(0, $.getTweetsCountOfUser("I am not even a user"));
	}

	/**
	 * Tests method for {@link UserTweetsQueries#clearData()}.
	 */
	@Test
	public final void getTweetsCountOfUserAfterClearDataShouldReturn0() {
		$.clearData();
		assertEquals(0, $.getTweetsCountOfUser("Ziv"));
	}

}
