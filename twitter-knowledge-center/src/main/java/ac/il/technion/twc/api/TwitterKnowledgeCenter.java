package ac.il.technion.twc.api;

import java.util.LinkedList;
import java.util.List;

import ac.il.technion.twc.api.parsers.TweetToMemory;
import ac.il.technion.twc.api.tweet_extractors.TweetExtractor;
import ac.il.technion.twc.api.visitor.Visitor;

/**
 * This class is meant to act as a wrapper to test your functionality. You
 * should implement all its methods and not change any of their signatures. You
 * can also implement an argumentless constructor if you wish.
 * 
 * @author Gal Lalouche
 */
public class TwitterKnowledgeCenter implements TwitterQueryAPI {
  private final TweetToMemory preserver;
  List<Tweet> tweets;

  /**
   * @param importerExporter
   *          for storing and loading the tweets
   */
  public TwitterKnowledgeCenter(final TweetToMemory importerExporter) {
    preserver = importerExporter;
    tweets = new LinkedList<>();
  }

  /**
   * Loads the data from an array of lines
   * 
   * @param lines
   *          An array of lines, each line formatted as <time (dd/MM/yyyy
   *          HH:mm:ss)>,<tweet id>[,original tweet]
   */
  @Override
  public void importData(final String[] lines, final TweetExtractor extractor) {
    final List<Tweet> inputList = new LinkedList<>();
    for (final String tweetString : lines)
      inputList.add(extractor.extractTweet(tweetString));
    preserver.reserve(inputList);

  }

  /**
   * Loads the index, allowing for queries on the data that was imported using
   * {@link TwitterKnowledgeCenter#importData(String[])}. setupIndex will be
   * called before any queries can be run on the system
   * 
   * @throws Exception
   *           If for any reason, loading the index failed
   */
  @Override
  public void setupIndex(final List<Visitor> visitors) {
    tweets = preserver.recover();
    for (final Visitor v : visitors)
      accept(v);
  }

  @Override
  public List<Tweet> getTweets() {
    return tweets;
  }

  /**
   * Cleans up all persistent data from the system; this method will be called
   * before every test, to ensure that all tests are independent.
   */
  @Override
  public void cleanPersistentData() {
    preserver.clear();
  }

  @Override
  public void accept(final Visitor v) {
    v.clearData();
    v.visit(this);
  }
}