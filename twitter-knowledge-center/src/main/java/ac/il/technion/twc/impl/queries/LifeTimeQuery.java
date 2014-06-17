package ac.il.technion.twc.impl.queries;

import ac.il.technion.twc.api.Tweet;
import ac.il.technion.twc.api.TwitterQueryAPI;
import ac.il.technion.twc.api.visitor.Visitor;
import ac.il.technion.twc.impl.retweet_info.UnionFind;

/**
 * Created by Matan on 5/31/14.
 */
public class LifeTimeQuery implements Visitor {
    UnionFind uf = new UnionFind();

    @Override
    public void visit(TwitterQueryAPI twitter) {
        for (Tweet t : twitter.getTweets())
            uf.addElement(t);
        uf.unionAllTweets();
    }

    public String getLifeTime(String id) {
        return uf.getLifeTimeOfTweet(id).toString();
    }

	@Override
	public void clearData() {
		uf.clear();
	}
}
