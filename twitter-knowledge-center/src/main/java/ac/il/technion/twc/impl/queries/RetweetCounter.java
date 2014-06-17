package ac.il.technion.twc.impl.queries;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ac.il.technion.twc.api.Tweet;
import ac.il.technion.twc.api.TwitterQueryAPI;
import ac.il.technion.twc.api.visitor.Visitor;
import ac.il.technion.twc.impl.queries.OriginFinder.NotFoundException;

/**
 * Count the amount of retweet of a given tweet
 * 
 * @author Ziv Ronen
 * @date 17.06.2014
 * @mail akarks@gmail.com
 */
public class RetweetCounter implements Visitor {

  Map<String, Integer> retweetsCountById = new HashMap<>();
  private List<Tweet> tweets;

  @Override
  public void visit(final TwitterQueryAPI twitter) {
    tweets = Collections.unmodifiableList(twitter.getTweets());
  }

  @Override
  public void clearData() {
    tweets = Collections.<Tweet> emptyList();
    retweetsCountById = Collections.<String, Integer> emptyMap();
  }

  /**
   * @param finder
   *          for getting the original tweet
   */
  public void init(final OriginFinder finder) {
    for (final Tweet tweet : tweets) {
      if (tweet.isRetweet())
        continue;
      try {
        final Tweet base = finder.origin(tweet);
        retweetsCountById.put(base.getTweetID(),
            Integer.valueOf(1 + getRetweetCount(base.getTweetID())));
      } catch (final NotFoundException e) {
        continue;
      }
    }
    retweetsCountById = Collections.unmodifiableMap(retweetsCountById);
  }

  /**
   * @param string
   *          the id of the base tweet
   * @return the number of base tweets
   */
  public int getRetweetCount(final String string) {
    return !retweetsCountById.containsKey(string) ? 0 : retweetsCountById.get(
        string).intValue();
  }

}
