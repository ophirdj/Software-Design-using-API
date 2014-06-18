package ac.il.technion.twc;

import java.util.ArrayList;
import java.util.List;

import ac.il.technion.twc.api.TwitterKnowledgeCenter;
import ac.il.technion.twc.api.TwitterQueryAPI;
import ac.il.technion.twc.api.parsers.TweetStringToMemory;
import ac.il.technion.twc.api.tweet_extractors.JsonTweetExtractor;
import ac.il.technion.twc.api.tweet_extractors.StringTweetExtractor;
import ac.il.technion.twc.api.visitor.Visitor;
import ac.il.technion.twc.impl.queries.HashtagsHighestCouplingFinder;
import ac.il.technion.twc.impl.queries.OriginFinder;
import ac.il.technion.twc.impl.queries.RetweetCounter;
import ac.il.technion.twc.impl.queries.TweetBetweenFinder;
import ac.il.technion.twc.impl.queries.UserTweetsQueries;

/**
 * This class is meant to act as a wrapper to test your functionality. You
 * should implement <b>only</b> the methods matching the selected functionality
 * and not change any method's signature. The methods you choose not to
 * implement may remain with their default (i.e., exception throwing) behaviour.
 * You can also implement an argumentless constructor if you wish, and any
 * number of new public methods.
 * 
 * @author Gal Lalouche
 */
public class FunctionalityTester {

  OriginFinder originFinder = new OriginFinder();
  RetweetCounter retweetCounter = new RetweetCounter();
  TweetBetweenFinder tweetBetweenFinder = new TweetBetweenFinder();
  UserTweetsQueries userTweetsQueries = new UserTweetsQueries();
  HashtagsHighestCouplingFinder hashtagsHighestCouplingFinder =
      new HashtagsHighestCouplingFinder();

  TwitterQueryAPI tkc = new TwitterKnowledgeCenter(new TweetStringToMemory());

  List<Visitor> queries = new ArrayList<>();

  /**
   * 
   */
  public FunctionalityTester() {
    queries.add(originFinder);
    queries.add(retweetCounter);
    queries.add(tweetBetweenFinder);
    queries.add(userTweetsQueries);
    queries.add(hashtagsHighestCouplingFinder);
  }

  /**
   * Loads the data from an array of lines
   * 
   * @param lines
   *          An array of lines, each line formatted as <time (dd/MM/yyyy
   *          HH:mm:ss)>,<tweet id>[,original tweet]
   * @throws Exception
   *           If for any reason, handling the data failed
   */
  public void importData(final String[] lines) throws Exception {
    tkc.importData(lines, new StringTweetExtractor());
  }

  /**
   * Loads the data from an array of JSON lines
   * 
   * @param lines
   *          An array of lines, each line is a JSON string
   * @throws Exception
   *           If for any reason, handling the data failed
   */
  public void importDataJson(final String[] lines) throws Exception {
    tkc.importData(lines, new JsonTweetExtractor());
  }

  /**
   * Loads the index, allowing for queries on the data that was imported using
   * {@link FunctionalityTester#importData(String[])}. setupIndex will be called
   * before any queries can be run on the system
   * 
   * @throws Exception
   *           If for any reason, loading the index failed
   */
  public void setupIndex() throws Exception {
    tkc.setupIndex(queries);
    retweetCounter.init(originFinder);
  }

  // GROUP A

  /**
   * Gets original tweet's id from a retweet; if the input itself is an original
   * tweet, then this function returns its id
   * 
   * @param tweetId
   *          an id of a tweet
   * 
   * @return the Id of the original tweet
   * @throws Exception
   *           If it is not possible to complete the operation
   */
  public String getOriginalTweetsId(final String tweetId) throws Exception {
    return originFinder.origin(tweetId);
  }

  /**
   * Gets an id of <i>a</i> tweet that was published between two dates
   * 
   * @param t1
   *          A date string in the format of <b>dd/MM/yyyy HH:mm:ss</b>; The
   *          tweet should have been published after this time.
   * @param t2
   *          A date string in the format of <b>dd/MM/yyyy HH:mm:ss</b>; The
   *          tweet should have been published before this time.
   * @return The id of a tweet published between t1 and t2
   * @throws Exception
   *           If it is not possible to complete the operation
   */
  public String findTweetInTime(final String t1, final String t2)
      throws Exception {
    return tweetBetweenFinder.findTweetBetween(
        TweetBetweenFinder.dateFromString(t1),
        TweetBetweenFinder.dateFromString(t2));
  }

  /**
   * Gets the id of the first tweet (by publication date) made by a user
   * 
   * @param userId
   *          the id of the user
   * @return The id of the user's first tweet
   * @throws Exception
   *           If it is not possible to complete the operation
   */
  public String getFirstTweet(final String userId) throws Exception {
    return userTweetsQueries.getUserFirstTweet(userId);
  }

  // GROUP B

  /**
   * Gets the number of retweets (recursive) of an original tweet
   * 
   * @param tweetId
   *          the id of the tweet
   * @return The number of retweets
   */
  public String numberOfRetweets(final String tweetId) {
    return Integer.toString(retweetCounter.getRetweetCount(tweetId));
  }

  /**
   * Gets the number of tweets or retweets made by a specific user
   * 
   * @param userId
   *          the id of the user
   * @return The number of tweets made by the user
   */
  public String numberTweetsByUser(final String userId) {
    return Integer.toString(userTweetsQueries.getTweetsCountOfUser(userId));
  }

  // GROUP C
  /**
   * Gets the top k coupled hashtags
   * 
   * @param k
   *          the number of hashtags
   * @return The number of tweets made by the user
   * @throws Exception
   *           If it is not possible to complete the operation
   */
  public String[] hashtagCoupling(final int k) throws Exception {
    return hashtagsHighestCouplingFinder.kMostCoupled(k);
  }

  /**
   * Cleans up all persistent data from the system; this method will be called
   * before every test, to ensure that all tests are independent.
   */
  public void cleanPersistentData() {
    tkc.cleanPersistentData();
  }
}