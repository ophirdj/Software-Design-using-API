package ac.il.technion.twc.api;

import ac.il.technion.twc.api.parsers.TweetToMemory;
import ac.il.technion.twc.api.tweet_extractors.TweetExtractor;
import ac.il.technion.twc.api.visitor.Visitor;

import java.util.LinkedList;
import java.util.List;


/**
 * This class is meant to act as a wrapper to test your functionality. You should implement all its methods
 * and not change any of their signatures. You can also implement an argumentless constructor if you wish.
 *
 * @author Gal Lalouche
 */
public class TwitterKnowledgeCenter implements TwitterQueryAPI{
    private TweetToMemory preserver;
    List<Tweet> tweets;

    public TwitterKnowledgeCenter(TweetToMemory importerExporter) {
        preserver = importerExporter;
        tweets = new LinkedList<Tweet>();
    }

    /**
     * Loads the data from an array of lines
     *
     * @param lines An array of lines, each line formatted as <time (dd/MM/yyyy HH:mm:ss)>,<tweet id>[,original tweet]
     * @throws Exception If for any reason, handling the data failed
     */
    @Override
    public void importData(String[] lines, TweetExtractor extractor){
        List<Tweet> inputList = new LinkedList<Tweet>();
        for (String tweetString : lines)
            inputList.add(extractor.extractTweet(tweetString));
        preserver.reserve(inputList);

    }

    /**
     * Loads the index, allowing for queries on the data that was imported using
     * {@link TwitterKnowledgeCenter#importData(String[])}. setupIndex will be called before any queries can be run
     * on the system
     *
     * @throws Exception If for any reason, loading the index failed
     */
    @Override
    public void setupIndex(List<Visitor> visitors){
        tweets = preserver.recover();
        for(Visitor v : visitors){
        	accept(v);
        }
    }


    @Override
    public List<Tweet> getTweets() {
        return tweets;
    }

    /**
     * Cleans up all persistent data from the system; this method will be called before every test,
     * to ensure that all tests are independent.
     */
    @Override
    public void cleanPersistentData() {
        preserver.clear();
    }

	@Override
	public void accept(Visitor v) {
		v.clearData();
        v.visit(this);
	}
}