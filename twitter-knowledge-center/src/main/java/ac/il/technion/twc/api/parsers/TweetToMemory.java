package ac.il.technion.twc.api.parsers;

import java.util.List;

import ac.il.technion.twc.api.Tweet;

public interface TweetToMemory {

    /**
     * @param tweets - Receives a list of Tweets to write to a persistent medium and writes them to it.
     */
	public void reserve(List<Tweet> tweets);

    /**
     * @return - The Tweets saved in the persistent medium
     */
	public List<Tweet> recover();

    /**
     * Deletes the persistent medium.
     */
	public void clear();
}
