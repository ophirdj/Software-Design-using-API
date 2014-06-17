package ac.il.technion.twc.api;

import ac.il.technion.twc.api.tweet_extractors.TweetExtractor;
import ac.il.technion.twc.api.visitor.Visitable;
import ac.il.technion.twc.api.visitor.Visitor;

import java.util.List;

/**
 * Created by Matan on 5/31/14.
 */
public interface TwitterQueryAPI extends Visitable {

    /**
     * @param lines - The strings to import
     * @param extractor - The type of extractor matching the string format.
     */
    void importData(String[] lines, TweetExtractor extractor);

    /**
     * Look at the Targil srsly.
     * @param visitors - The visitors that need the "visit" after the setupIndex finishes indeixing all its data.
     */
    void setupIndex(List<Visitor> visitors);

    /**
     * @return - Returns all the tweets that were imported until now.
     * <b>This method is crucial</b> to the visitor pattern. The visitors receive the TwitterQueryAPI instance via the visit() method
     * and invoke this to build the visitor's database and run queries on it.
     */
    List<Tweet> getTweets();

    /**
     * You all know what this does.
     */
    void cleanPersistentData();
}

