package ac.il.technion.twc.api.tweet_extractors;

import java.util.Date;
import java.util.List;

import ac.il.technion.twc.api.Tweet;
import ac.il.technion.twc.api.exceptions.ParsingErrorException;

import com.twitter.Extractor;

import twitter4j.Status;
import twitter4j.TwitterException;
import twitter4j.TwitterObjectFactory;

/**
 * Created by Matan on 5/31/14.
 */
public class JsonTweetExtractor implements TweetExtractor{
	@Override
	public Tweet extractTweet(String s) {
		Status stat;
		try {
			stat = TwitterObjectFactory.createStatus(s);
		} catch (TwitterException e) {
			e.printStackTrace();
			throw new ParsingErrorException();
		}
		
		Long id = stat.getId();
		Status orig = stat.getRetweetedStatus();
		String origId = (orig == null) ? null : ((Long)orig.getId()).toString();
		String txt = stat.getText();
		List<String> hashTags = new Extractor().extractHashtags(txt);
		Date d =stat.getCreatedAt();
		return new Tweet(id.toString(), origId, stat.getCreatedAt(), hashTags);
	}
}
