package ac.il.technion.twc.api.visitor;

import ac.il.technion.twc.api.Tweet;
import ac.il.technion.twc.api.TwitterQueryAPI;

/**
 * Created by Matan on 5/31/14.
 */
public class DailyHistogramQuery implements Visitor {
    static final int TIME_UNIT_BYDAYS = 7; //this time it's a week, maybe next time it'll be a month?
    Integer[] tweetsByDay = new Integer[7];
    Integer[] retweetsByDay = new Integer[7];

    @Override
    public void visit(TwitterQueryAPI twitter) {
    	initArrays();
        for (Tweet tweet : twitter.getTweets()){
            retweetsByDay[tweet.getDay()] += tweet.isRetweet() ? 1 : 0;
            tweetsByDay[tweet.getDay()]++;
        }
    }

    public String[] getHistogram(){
        return mergeTweetsRetweetsHistogram(tweetsByDay,retweetsByDay);
    }


    private void initArrays() {
        for(int i=0; i<TIME_UNIT_BYDAYS ; i++){
            tweetsByDay[i]=0;
            retweetsByDay[i]=0;
        }
    }

    private String[] mergeTweetsRetweetsHistogram(Integer[] tweetsByDay, Integer[] retweetsByDay) {
        String[] histogram = new String[TIME_UNIT_BYDAYS];
        for (int i = 0; i < TIME_UNIT_BYDAYS; i++)
            histogram[i] = tweetsByDay[i].toString() + "," + retweetsByDay[i].toString();
        return histogram;
    }

	@Override
	public void clearData() {
		initArrays();
	}
}
