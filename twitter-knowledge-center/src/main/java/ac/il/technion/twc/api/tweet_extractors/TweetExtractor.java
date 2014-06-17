package ac.il.technion.twc.api.tweet_extractors;

import java.util.Date;
import java.util.List;

import ac.il.technion.twc.api.Tweet;

/**
 * Created by Matan on 5/31/14.
 */
public interface TweetExtractor {

    /**
     * @param s - A string representation of a Tweet in some format (e.g: JSON, XML etc...)
     * @return - A Tweet object that's represented by the string <b>s</b>
     */
    Tweet extractTweet(String s);
}
