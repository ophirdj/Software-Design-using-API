package ac.il.technion.twc.impl.queries;

import ac.il.technion.twc.api.Tweet;
import ac.il.technion.twc.api.TwitterQueryAPI;
import ac.il.technion.twc.api.visitor.Visitor;
import ac.il.technion.twc.impl.retweet_info.UnionFind;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Matan on 5/31/14.
 */
public class HashtagPopularityQuery implements Visitor {
    Map<String,Integer> hashtagPopularity = new HashMap<String, Integer>();
    UnionFind uf = new UnionFind();

    @Override
    public void visit(TwitterQueryAPI twitter) {
        for (Tweet t : twitter.getTweets())
            uf.addElement(t);
        uf.unionAllTweets();

        for (Tweet tweet : twitter.getTweets()){
            if (tweet.isRetweet()){ continue;}
            addHashtagPopularity(twitter, tweet);
        }
    }

    private void addHashtagPopularity(TwitterQueryAPI twitter, Tweet tweet) {
        for (String s: tweet.getHashtags()){
            if (!hashtagPopularity.containsKey(s)){
                hashtagPopularity.put(s,0);
            }
            hashtagPopularity.put(s,hashtagPopularity.get(s)+uf.getNumberOfRetweets(tweet.getTweetID()));
        }
    }

    public String getHashtagPopularity(String hashtag) {
    	if(hashtagPopularity.containsKey(hashtag))
    		return hashtagPopularity.get(hashtag).toString();
    	return "0";
    }

	@Override
	public void clearData() {
        hashtagPopularity.clear();
        uf.clear();
	}
}
