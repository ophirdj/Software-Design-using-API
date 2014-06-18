package ac.il.technion.twc.impl.queries;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import ac.il.technion.twc.api.Tweet;
import ac.il.technion.twc.api.TwitterQueryAPI;
import ac.il.technion.twc.api.visitor.Visitor;

/**
 * <br>
 * Finds the ancestor tweet (that isn't a retweet) of a tweet.</br>For example,
 * say we have 3 tweets: <code>A</code>, <code>B</code>, and <code>C</code>. <br>
 * <code>A</code> is a simple tweet, <code>B</code> is a retweet of
 * <code>A</code>, and <code>C</code> is a retweet of <code>B</code>.</br>Then:
 * <code>origin(A)</code> = <code>origin(B)</code> = <code>origin(C)</code> =
 * <code>A</code>.
 * 
 * @author Ziv Ronen
 * @date 07.05.2014
 * @mail akarks@gmail.com
 * 
 */
public class OriginFinder implements Visitor {

  private Map<String, String> relation;
  private Set<String> baseTweets;

  /**
	 * 
	 */
  public OriginFinder() {
    clearData();
  }

  @Override
  public void visit(final TwitterQueryAPI twitter) {
    relation = new HashMap<>();
    final Set<String> buildingBaseTweetSet = new HashSet<>();
    for (final Tweet tweet : twitter.getTweets())
      if (!tweet.isRetweet())
        buildingBaseTweetSet.add(tweet.getTweetID());
      else
        relation.put(tweet.getTweetID(), tweet.getParentTweet());
    baseTweets = Collections.unmodifiableSet(buildingBaseTweetSet);
  }

  @Override
  public void clearData() {
    relation = Collections.<String, String> emptyMap();
    baseTweets = Collections.<String> emptySet();
  }

  /**
   * @param tweetId
   *          A id of tweet
   * @return The ancestor tweet (that isn't a retweet) of given tweet.
   * @throws NotFoundException
   *           If no ancestor tweet that isn't a retweet exists.
   */
  public String origin(final String tweetId) throws NotFoundException {
    final String rootId = findRootAux(tweetId);
    if (!baseTweets.contains(rootId))
      throw new NotFoundException();
    return rootId;
  }

  private String findRootAux(final String currentId) {
    if (!relation.containsKey(currentId))
      return currentId;
    final String rootId = findRootAux(relation.get(currentId));
    relation.put(currentId, rootId);
    return rootId;
  }

  /**
   * An exception represent missing root in findRoot calling
   * 
   * @author Ziv Ronen
   * @date 07.05.2014
   * @mail akarks@gmail.com
   * 
   */
  public static class NotFoundException extends Exception {

    /**
		 * 
		 */
    public static final long serialVersionUID = 1957959540233599796L;

  }
}
