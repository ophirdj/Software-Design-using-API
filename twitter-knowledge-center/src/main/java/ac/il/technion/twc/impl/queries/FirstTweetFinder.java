package ac.il.technion.twc.impl.queries;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import ac.il.technion.twc.api.Tweet;
import ac.il.technion.twc.api.TwitterQueryAPI;
import ac.il.technion.twc.api.visitor.Visitor;

/**
 * Finds first (chronologically) tweet of a user.
 * 
 * 
 * @author Ophir De Jager
 * 
 */
public class FirstTweetFinder implements Visitor {

	private List<Tweet> tweets;

	@Override
	public void visit(final TwitterQueryAPI twitter) {
		tweets = Collections.unmodifiableList(twitter.getTweets());

	}

	@Override
	public void clearData() {
		tweets = Collections.<Tweet> emptyList();
	}

	/**
	 * @param userID
	 * @return Tweet ID of first tweet of user.
	 * @throws NotFoundException
	 *             If no tweets were found for this user.
	 */
	public String getUserFirstTweet(final String userID)
			throws NotFoundException {
		String $ = null;
		Date firstDate = new Date(Long.MAX_VALUE);
		for (final Tweet tweet : tweets)
			if (tweet.getUserID() != null
					&& tweet.getUserID().toString().equals(userID)
					&& !firstDate.after(tweet.getDate())) {
				firstDate = tweet.getDate();
				$ = tweet.getTweetID();
			}
		if ($ == null)
			throw new NotFoundException();
		return $;
	}

	/**
	 * @author Ophir De Jager
	 * 
	 */
	public static class NotFoundException extends RuntimeException {

		private static final long serialVersionUID = 2792149936612616492L;
	}

}
