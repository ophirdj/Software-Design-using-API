package ac.il.technion.twc.impl.queries;

import ac.il.technion.twc.api.Tweet;
import ac.il.technion.twc.api.TwitterQueryAPI;
import ac.il.technion.twc.api.visitor.Visitor;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by Matan on 5/31/14.
 */
public class TemporalHistogramQuery implements Visitor {
    static final int TIME_UNIT_BYDAYS = 7; //this time it's a week, maybe next time it'll be a month?
    Integer[] tweetsByDay = new Integer[TIME_UNIT_BYDAYS];
    Integer[] retweetsByDay = new Integer[TIME_UNIT_BYDAYS];
    List<Tweet> tweetList;
    Date date1;
    Date date2;

    @Override
    public void visit(TwitterQueryAPI twitter) {
        tweetList = twitter.getTweets();
    }

    public String[] getHistogram(String s1, String s2){
        stringToDates(s1,s2);
        initArrays();
        for (Tweet tweet : tweetList){
            boolean relevant = 
            		(tweet.getDate().getTime()>=date1.getTime()) && 
            		(tweet.getDate().getTime()<=date2.getTime());
            
            retweetsByDay[tweet.getDay()] += tweet.isRetweet()&&relevant ? 1 : 0;
            tweetsByDay[tweet.getDay()] += relevant ? 1 : 0;
        }
        return mergeTweetsRetweetsHistogram(tweetsByDay,retweetsByDay);
    }

    private void stringToDates(String s1, String s2) {
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        try {
            date1 = format.parse(s1);
            date2 = format.parse(s2);
        } catch (ParseException e) {
            e.printStackTrace();
        }
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
