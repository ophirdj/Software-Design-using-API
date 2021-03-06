package ac.il.technion.twc.api.tweet_extractors;

import java.util.List;

import twitter4j.Status;
import twitter4j.TwitterException;
import twitter4j.TwitterObjectFactory;
import twitter4j.User;
import ac.il.technion.twc.api.Tweet;
import ac.il.technion.twc.api.exceptions.ParsingErrorException;

import com.twitter.Extractor;

/**
 * Created by Matan on 5/31/14.
 */
public class JsonTweetExtractor implements TweetExtractor {
  @Override
  public Tweet extractTweet(final String s) {
    Status stat;
    try {
      stat = TwitterObjectFactory.createStatus(s);
    } catch (final TwitterException e) {
      e.printStackTrace();
      throw new ParsingErrorException();
    }

    final Long id = Long.valueOf(stat.getId());
    final Status orig = stat.getRetweetedStatus();
    final String origId =
        orig == null ? null : Long.valueOf(orig.getId()).toString();
    final String txt = stat.getText();
    final List<String> hashTags = new Extractor().extractHashtags(txt);
    final User user = stat.getUser();
    String userID = null;
    if (user != null)
      userID = Long.toString(user.getId());
    return new Tweet(id.toString(), origId, stat.getCreatedAt(), hashTags,
        userID);
  }
}
