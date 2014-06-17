package ac.il.technion.twc.impl.queries;

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
public class UserTweetsCounter implements Visitor {

  Map<Long, Integer> tweetsCountByUserId = new HashMap<>();

  @Override
  public void visit(final TwitterQueryAPI twitter) {
    for (final Tweet tweet : twitter.getTweets()) {
      final Long userId = tweet.getUserID();
      if (null == userId)
        continue;
      tweetsCountByUserId.put(userId,
          Integer.valueOf(1 + getTweetsCountOfUser(userId)));
    }
  }

  @Override
  public void clearData() {
    tweetsCountByUserId.clear();
  }

  /**
   * @param userId
   *          The id of the user
   * @return the number of tweets he/she posted
   */
  public int getTweetsCountOfUser(final Long userId) {
    return tweetsCountByUserId.containsKey(userId) ? 0 : tweetsCountByUserId
        .get(userId).intValue();
  }

}
