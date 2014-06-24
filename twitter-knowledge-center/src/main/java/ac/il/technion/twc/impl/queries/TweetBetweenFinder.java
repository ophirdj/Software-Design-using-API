package ac.il.technion.twc.impl.queries;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import ac.il.technion.twc.api.Tweet;
import ac.il.technion.twc.api.TwitterQueryAPI;
import ac.il.technion.twc.api.visitor.Visitor;

/**
 * @author Ziv Ronen
 * @date 17.06.2014
 * @mail akarks@gmail.com
 */
public class TweetBetweenFinder implements Visitor {

	private static SimpleDateFormat QUERY_DATE_FORMAT = new SimpleDateFormat(
			"dd/MM/yyyy HH:mm:ss"); //$NON-NLS-1$
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
	 * @param from
	 *            The start of the time interval
	 * @param to
	 *            The end of the time interval
	 * @return An id of tweet in the given interval
	 * @throws NotTweetInRangeException
	 *             If no such tweet exist
	 */
	public String findTweetBetween(final Date from, final Date to)
			throws NotTweetInRangeException {
		for (final Tweet tweet : tweets)
			if (!tweet.getDate().before(from) && !tweet.getDate().after(to))
				return tweet.getTweetID();
		throw new NotTweetInRangeException();
	}

	/**
	 * Exeption to throw if no tweet exists in the given range
	 * 
	 * @author Ziv Ronen
	 * @date 17.06.2014
	 * @mail akarks@gmail.com
	 */
	public static class NotTweetInRangeException extends Exception {

		/**
     * 
     */
		private static final long serialVersionUID = 6232645509562698747L;

	}

	/**
	 * @param str
	 *            string representing a date
	 * @return the date of the tweet
	 * @throws ParseException
	 *             if the string is not a valid date
	 */
	public static Date dateFromString(final String str) throws ParseException {
		return QUERY_DATE_FORMAT.parse(str);
	}

}
