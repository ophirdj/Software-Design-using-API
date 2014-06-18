package ac.il.technion.twc;

import java.util.LinkedList;
import java.util.List;

import ac.il.technion.twc.api.TwitterKnowledgeCenter;
import ac.il.technion.twc.api.TwitterQueryAPI;
import ac.il.technion.twc.api.parsers.TweetJSONToMemory;
import ac.il.technion.twc.api.tweet_extractors.JsonTweetExtractor;
import ac.il.technion.twc.api.tweet_extractors.StringTweetExtractor;
import ac.il.technion.twc.api.visitor.DailyHistogramQuery;
import ac.il.technion.twc.api.visitor.Visitor;
import ac.il.technion.twc.impl.queries.HashtagPopularityQuery;
import ac.il.technion.twc.impl.queries.LifeTimeQuery;
import ac.il.technion.twc.impl.queries.TemporalHistogramQuery;

/**
 * This class is meant to act as a wrapper to test your functionality. You
 * should implement all its methods and not change any of their signatures. You
 * can also implement an argumentless constructor if you wish, and any number of
 * new public methods
 * 
 * @author Gal Lalouche
 */
public class FuntionalityTester {
  TwitterQueryAPI tkc = new TwitterKnowledgeCenter(new TweetJSONToMemory());

  HashtagPopularityQuery hashtagAnalyzer = new HashtagPopularityQuery();
  LifeTimeQuery lifeTimeAnalyzer = new LifeTimeQuery();
  DailyHistogramQuery histogramAnalyzer = new DailyHistogramQuery();
  TemporalHistogramQuery temporalHistogramAnalyzer =
      new TemporalHistogramQuery();

  List<Visitor> queries = new LinkedList<Visitor>();

  public FuntionalityTester() {
    queries.add(hashtagAnalyzer);
    queries.add(lifeTimeAnalyzer);
    queries.add(histogramAnalyzer);
    queries.add(temporalHistogramAnalyzer);
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
   * {@link TwitterKnowledgeCenter#importData(String[])}. setupIndex will be
   * called before any queries can be run on the system
   * 
   * @throws Exception
   *           If for any reason, loading the index failed
   */
  public void setupIndex() throws Exception {
    tkc.setupIndex(queries);
  }

  /**
   * Gets the lifetime of the tweet, in milliseconds. You may assume we will ask
   * about the lifetime of a retweet, but only about the lifetime of an original
   * tweet.
   * 
   * @param tweetId
   *          The tweet's identifier
   * @return A string, counting the number of milliseconds between the tweet's
   *         publication and its last retweet (recursive)
   * @throws Exception
   *           If it is not possible to complete the operation
   */
  public String getLifetimeOfTweets(final String tweetId) throws Exception {
    return lifeTimeAnalyzer.getLifeTime(tweetId);
  }

  /**
   * Gets the weekly histogram of all tweet and retweet data
   * 
   * @return An array of strings, each string in the format of
   *         ("<number of tweets (including retweets), number of retweets only>"
   *         ), for example: ["100, 10","250,20",...,"587,0"]. The 0th index of
   *         the array is Sunday.
   */
  public String[] getDailyHistogram() {
    return histogramAnalyzer.getHistogram();
  }

  /**
   * Gets the number of (recursive) retweets made to all original tweets made
   * that contain a specific hashtag
   * 
   * @param hashtag
   *          The hashtag to check
   * @return A string, in the format of a number, contain the number of retweets
   */
  public String getHashtagPopularity(final String hashtag) {
    return hashtagAnalyzer.getHashtagPopularity(hashtag);
  }

  /**
   * Gets the weekly histogram of all tweet data
   * 
   * @param t1
   *          A date string in the format of <b>dd/MM/yyyy HH:mm:ss</b>; all
   *          tweets counted in the histogram should have been published
   *          <b>after<\b> t1.
   * @param t2
   *          A date string in the format of <b>dd/MM/yyyy HH:mm:ss</b>; all
   *          tweets counted in the histogram should have been published
   *          <b>before<\b> t2.
   * @return An array of strings, each string in the format of
   *         ("<number of tweets (including retweets), number of retweets only>"
   *         ), for example: ["100, 10","250,20",...,"587,0"]. The 0th index of
   *         the array is Sunday.
   * @throws Exception
   *           If it is not possible to complete the operation
   */
  public String[] getTemporalHistogram(final String t1, final String t2)
      throws Exception {
    return temporalHistogramAnalyzer.getHistogram(t1, t2);
  }

  /**
   * Cleans up all persistent data from the system; this method will be called
   * before every test, to ensure that all tests are independent.
   */
  public void cleanPersistentData() {
    tkc.cleanPersistentData();
  }
}
