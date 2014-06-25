package ac.il.technion.twc.time;

import org.junit.Before;
import org.junit.Test;

import ac.il.technion.twc.FunctionalityTester;
import ac.il.technion.twc.impl.queries.TweetBetweenFinder.NotTweetInRangeException;

/**
 * Assert time constraints of methods of {@link FunctionalityTester}.
 * 
 * @author Ophir De Jager
 * 
 */
public class QueryTimeConstraintsTest {

  private static final int BASE_TWEETS = 2000;
  private static final int NUM_USERS = 10;
  private static final String[] lines = TestUtils.generateTweets(BASE_TWEETS,
      5, 3, NUM_USERS, 0);
  private static final int linesLengthApproximation = 312000;

  // imported data and set up index
  private final FunctionalityTester $ = new FunctionalityTester();

  /**
   * Set needed data
   * 
   * @throws Exception
   */
  @Before
  public void setUp() throws Exception {
    $.importDataJson(lines);
    $.setupIndex();
  }

  /**
   * Set needed data
   * 
   * @throws Exception
   */
  @Before
  public void tearDown() throws Exception {
    $.cleanPersistentData();
  }

  /**
   * Test method for {@link FunctionalityTester#findTweetInTime(String, String)}
   * 
   * @throws Exception
   */
  @Test(timeout = linesLengthApproximation / 2)
  public final void findTweetInTimeShouldRun500microForEachTweet()
      throws Exception {
    try {
      $.findTweetInTime("11/10/1999 10:10:10", "11/10/1999 10:10:10");
    } catch (final NotTweetInRangeException e) {
      //
    }
  }

  /**
   * Test method for {@link FunctionalityTester#getFirstTweet(String)}
   * 
   * @throws Exception
   */
  @Test(timeout = linesLengthApproximation / 2)
  public final void getFirstTweetShouldRun500microForEachTweet()
      throws Exception {
    $.getFirstTweet("0");
  }

  /**
   * Test method for {@link FunctionalityTester#getOriginalTweetsId(String)}
   * 
   * @throws Exception
   */
  @Test(timeout = BASE_TWEETS * linesLengthApproximation / 2)
  public final void getOriginalTweetsIdShouldRun500microForEachTweet()
      throws Exception {
    for (int i = 0; i < BASE_TWEETS; ++i)
      $.getOriginalTweetsId(Integer.toString(i));
  }

  /**
   * Test method for {@link FunctionalityTester#numberOfRetweets(String)}
   * 
   * @throws Exception
   */
  @Test(timeout = BASE_TWEETS * 10)
  public final void getOriginalTweetsIdShouldRun10ms() throws Exception {
    for (int i = 0; i < BASE_TWEETS; ++i)
      $.numberOfRetweets(Integer.toString(i));
  }

  /**
   * Test method for {@link FunctionalityTester#numberTweetsByUser(String)}
   * 
   * @throws Exception
   */
  @Test(timeout = NUM_USERS * 10)
  public final void numberTweetsByUserShouldRun10ms() throws Exception {
    for (int i = 0; i < NUM_USERS; ++i)
      $.numberTweetsByUser(Integer.toString(i));
  }

  /**
   * Test method for {@link FunctionalityTester#hashtagCoupling(int)}
   * 
   * @throws Exception
   */
  @Test(timeout = 55 * 10)
  public final void hashtagCouplingShouldRun10msForKVal() throws Exception {
    for (int k = 1; k <= 10; ++k)
      $.hashtagCoupling(k);
  }

}
