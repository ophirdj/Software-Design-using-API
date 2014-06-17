package ac.il.technion.twc.impl.retweet_info;

import java.util.Date;

import ac.il.technion.twc.api.Tweet;

public interface RetweetQueryEngine {

	void addElement(Tweet element);

	void unionAllTweets();

	Date getLatestRetweetTime(String id);

	Long getLifeTimeOfTweet(String id);

	Integer getNumberOfRetweets(String id);

	void clear();

}
