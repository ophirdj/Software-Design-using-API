package ac.il.technion.twc.api;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by Matan on 5/7/14.
 */
public class Tweet implements Serializable{
    private String tweetId;
    private String originalTweet;
    private Date tweetDate;
    private List<String> hashtags;

    public Tweet(String id, String origin, Date date,List<String> hashtags){
    	this.tweetId = id;
        this.tweetDate = date;
        this.originalTweet = origin;
        this.hashtags= hashtags;
    }

    public String getTweetID() {
        return tweetId;
    }


    public String getOriginalTweet() {
        return originalTweet;
    }

    public boolean isRetweet() {
        return this.originalTweet!=null;
    }

    public Date getDate(){
    	return tweetDate;
    }

    public long getTime() {
        return tweetDate.getTime();
    }

    public int getDay() {
        return tweetDate.getDay();
    }

    public List<String> getHashtags() {
        return hashtags;
    }

    @Override
    public boolean equals(Object o){
    	if(o==null || o.getClass() != this.getClass()){
    		return false;
    	}
    	Tweet t = (Tweet) o;    	
    	
    	return compareFields(this,t);
    }

	private static boolean compareFields(Tweet t1, Tweet t2) {
		if(!t1.tweetId.equals(t2.tweetId) || !t1.tweetDate.equals(t2.tweetDate))
			return false;
		if(t1.isRetweet())
			return t1.originalTweet.equals(t2.originalTweet);
		else
			return (t2.originalTweet == null);
	}

}
