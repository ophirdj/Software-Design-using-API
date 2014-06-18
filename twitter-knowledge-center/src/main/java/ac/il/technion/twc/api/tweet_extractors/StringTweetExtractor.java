package ac.il.technion.twc.api.tweet_extractors;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import ac.il.technion.twc.api.Tweet;

/**
 * Created by Matan on 5/8/14.
 */
public class StringTweetExtractor implements TweetExtractor {

  @Override
  public Tweet extractTweet(final String s) {
    final String originalID = extractOriginalTweetID(s);
    final List<String> hashtags = Collections.emptyList();
    final String id = extractID(s);
    final Date date = extractDate(s);

    final Tweet tweet = new Tweet(id, originalID, date, hashtags, null);
    return tweet;
  }

  private static String extractOriginalTweetID(final String tweet) {
    final String[] tweetString = splitTweetString(tweet);
    try {
      return tweetString[POSITION.ORIGINAL_TWEET_POSITION.getPosition()];
    } catch (final ArrayIndexOutOfBoundsException e) {
      return null;
    }
  }

  private static String extractID(final String tweet) {
    final String[] tweetString = splitTweetString(tweet);

    // Removes unwanted space character at the beginning
    return tweetString[POSITION.ID_POSITION.getPosition()];
  }

  private static Date extractDate(final String tweet) {
    final String[] tweetString = splitTweetString(tweet);
    final SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
    try {
      return format.parse(tweetString[POSITION.DATE_POSITION.getPosition()]);
    } catch (final ParseException e) {
      // e.printStackTrace();
      return null;
    }
  }

  private static String[] splitTweetString(final String tweet) {
    return tweet.split(", ");
  }

  private enum POSITION {
    DATE_POSITION(0), ID_POSITION(1), ORIGINAL_TWEET_POSITION(2);

    private final int position;

    POSITION(final int position) {
      this.position = position;
    }

    int getPosition() {
      return position;
    }
  }

}
