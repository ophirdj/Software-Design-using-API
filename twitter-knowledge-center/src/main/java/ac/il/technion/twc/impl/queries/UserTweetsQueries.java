package ac.il.technion.twc.impl.queries;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import ac.il.technion.twc.api.Tweet;
import ac.il.technion.twc.api.TwitterQueryAPI;
import ac.il.technion.twc.api.visitor.Visitor;

/**
 * @author Ziv Ronen
 * @date 17.06.2014
 * @mail akarks@gmail.com
 */
public class UserTweetsQueries implements Visitor {

  /**
   * @author Ophir De Jager
   * 
   */
  public static class NotFoundException extends RuntimeException {

    private static final long serialVersionUID = 2792149936612616492L;
  }

  Map<String, Integer> tweetsCountByUserId;
  Map<String, Tweet> firstTweetByUserId;

  @Override
  public void visit(final TwitterQueryAPI twitter) {
    tweetsCountByUserId = new HashMap<>();
    firstTweetByUserId = new HashMap<>();
    for (final Tweet tweet : twitter.getTweets()) {
      final String userId = tweet.getUserID();
      if (null == userId)
        continue;
      tweetsCountByUserId.put(userId,
          Integer.valueOf(1 + getTweetsCountOfUser(userId)));
      if (!firstTweetByUserId.containsKey(userId)
          || tweet.getDate().before(firstTweetByUserId.get(userId).getDate()))
        firstTweetByUserId.put(userId, tweet);
    }
    tweetsCountByUserId = Collections.unmodifiableMap(tweetsCountByUserId);
    firstTweetByUserId = Collections.unmodifiableMap(firstTweetByUserId);
  }

  @Override
  public void clearData() {
    tweetsCountByUserId = Collections.<String, Integer> emptyMap();
    firstTweetByUserId = Collections.<String, Tweet> emptyMap();
  }

  /**
   * @param userId
   *          The id of the user
   * @return the number of tweets he/she posted
   */
  public int getTweetsCountOfUser(final String userId) {
    return !tweetsCountByUserId.containsKey(userId) ? 0 : tweetsCountByUserId
        .get(userId).intValue();
  }

  /**
   * @param userID
   * @return Tweet ID of first tweet of user.
   * @throws UserTweetsQueries.NotFoundException
   *           If no tweets were found for this user.
   */
  public String getUserFirstTweet(final String userID) {
    if (!firstTweetByUserId.containsKey(userID))
      throw new UserTweetsQueries.NotFoundException();
    return firstTweetByUserId.get(userID).getTweetID();
  }

}
