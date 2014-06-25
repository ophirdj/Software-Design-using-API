package ac.il.technion.twc.api.parsers;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ac.il.technion.twc.api.Tweet;

/**
 * Tests for {@link TweetStringToMemory}
 * 
 * @author Ziv Ronen
 * @date 25.06.2014
 * @mail akarks@gmail.com
 */
public class TweetStringToMemoryTest {

  private static final Tweet RETWEET = new Tweet("avs53", "1256321", new Date(
      23805735L), null, null);
  private static final Tweet BASE_TWEET_WITHOUT_USER = new Tweet("saf353",
      null, new Date(23805735L), null, null);
  private static final Tweet BASE_TWEET_WITH_EMPTY_STRING_HASHTAG = new Tweet(
      "asf4w", null, new Date(2137748735L), Arrays.asList(""), null);
  private static final Tweet BASE_TWEET_WITH_HASHTAGS = new Tweet("2152", null,
      new Date(2137748735L), Arrays.asList("a", "b", "c"), null);
  private static final Tweet BASE_TWEET = new Tweet("2153", null, new Date(
      212452135L), null, "1245");
  private static final List<Tweet> TWEETS = Arrays.asList(BASE_TWEET, RETWEET);
  private final TweetStringToMemory $ = new TweetStringToMemory();

  /**
   * 
   */
  @Before
  public void setup() {
    $.clear();
  }

  /**
   * 
   */
  @After
  public void teardown() {
    $.clear();
  }

  /**
   * Tests for: <br>
   * - {@link TweetStringToMemory#encode(Tweet)}<br>
   * - {@link TweetStringToMemory#decode(String)}
   */
  private static void
      decodeTweetEncodingShouldEqualOriginalTweet(final Tweet t) {
    assertEquals(t, TweetStringToMemory.decode(TweetStringToMemory.encode(t)));
  }

  /**
   * 
   */
  @SuppressWarnings("static-method")
  @Test
  public final void decodingEncodingOfBaseTweetReturnEqualTweet() {
    decodeTweetEncodingShouldEqualOriginalTweet(BASE_TWEET);
  }

  /**
   * 
   */
  @SuppressWarnings("static-method")
  @Test
  public final void decodingEncodingOfBaseTweetWithHashtagsReturnEqualTweet() {
    decodeTweetEncodingShouldEqualOriginalTweet(BASE_TWEET_WITH_HASHTAGS);
  }

  /**
   * 
   */
  @SuppressWarnings("static-method")
  @Test
  public final void
      decodingEncodingOfBaseTweetWithEmptyStringHashtagReturnEqualTweet() {
    decodeTweetEncodingShouldEqualOriginalTweet(BASE_TWEET_WITH_EMPTY_STRING_HASHTAG);
  }

  /**
   * 
   */
  @SuppressWarnings("static-method")
  @Test
  public final void decodingEncodingOfBaseTweetWithoutUserReturnEqualTweet() {
    decodeTweetEncodingShouldEqualOriginalTweet(BASE_TWEET_WITHOUT_USER);
  }

  /**
   * 
   */
  @SuppressWarnings("static-method")
  @Test
  public final void decodingEncodingOfRetweetReturnEqualTweet() {
    decodeTweetEncodingShouldEqualOriginalTweet(RETWEET);
  }

  /**
   * 
   */
  @Test
  public void retrivedTweetsShouldEqualStoredTweets() {
    final List<Tweet> tweets = TWEETS;
    $.reserve(tweets);
    assertEquals(tweets, $.recover());
  }

  /**
   * 
   */
  @Test
  public void retrivedTweetsFromOtherObjectShouldEqualStoredTweets() {
    final List<Tweet> tweets = TWEETS;
    $.reserve(tweets);
    assertEquals(tweets, new TweetStringToMemory().recover());
  }

  /**
   * 
   */
  @Test
  public void retrivedTweetsShouldEqualStoredTweetsFromOtherObject() {
    final List<Tweet> tweets = TWEETS;
    new TweetStringToMemory().reserve(tweets);
    assertEquals(tweets, $.recover());
  }

  /**
   * 
   */
  @Test
  public void clearShouldRemoveStoredData() {
    $.reserve(TWEETS);
    $.clear();
    assertEquals(Collections.<Tweet> emptyList(), $.recover());
  }

  /**
   * 
   */
  @Test
  public void clearShouldRemoveOnlyPreviouslyStoredData() {
    $.reserve(TWEETS);
    $.clear();
    final List<Tweet> tweets = Arrays.asList(BASE_TWEET_WITHOUT_USER);
    $.reserve(tweets);
    assertEquals(tweets, $.recover());
  }

  /**
   * 
   */
  @Test
  public void clearFromOtherObjectShouldRemoveStoredData() {
    $.reserve(TWEETS);
    new TweetStringToMemory().clear();
    assertEquals(Collections.<Tweet> emptyList(), $.recover());
  }
}
