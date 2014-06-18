package ac.il.technion.twc.api;

import java.io.Serializable;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Created by Matan on 5/7/14.
 */
public class Tweet implements Serializable {
	private final String tweetId;
	private final String parentTweet;
	private final Date tweetDate;
	private final List<String> hashtags;
	private final String user;

	/**
	 * @param id
	 * @param origin
	 * @param date
	 * @param hashtags
	 * @param userID
	 */
	public Tweet(final String id, final String origin, final Date date,
			final List<String> hashtags, final String userID) {
		tweetId = id;
		tweetDate = date;
		parentTweet = origin;
		this.hashtags = Collections.unmodifiableList(hashtags);
		user = userID;
	}

	/**
	 * @return Tweet ID.
	 */
	public String getTweetID() {
		return tweetId;
	}

	/**
	 * @return ID of parent tweet.
	 */
	public String getParentTweet() {
		return parentTweet;
	}

	/**
	 * @return True if tweet is retweet
	 */
	public boolean isRetweet() {
		return parentTweet != null;
	}

	/**
	 * @return Date of tweet.
	 */
	public Date getDate() {
		return tweetDate;
	}

	/**
	 * @return Date of tweet (as long value).
	 */
	public long getTime() {
		return tweetDate.getTime();
	}

	/**
	 * @return Day of tweet.
	 */
	public int getDay() {
		return tweetDate.getDay();
	}

	/**
	 * @return Tweet's hashtags.
	 */
	public List<String> getHashtags() {
		return hashtags;
	}

	/**
	 * @return Tweet's user ID or null if doesn't exist.
	 */
	public String getUserID() {
		return user;
	}

	@Override
	public boolean equals(final Object o) {
		if (o == null || o.getClass() != this.getClass())
			return false;
		final Tweet t = (Tweet) o;

		return compareFields(this, t);
	}

	private static boolean compareFields(final Tweet t1, final Tweet t2) {
		if (!t1.tweetId.equals(t2.tweetId)
				|| !t1.tweetDate.equals(t2.tweetDate))
			return false;
		if (t1.isRetweet())
			return t1.parentTweet.equals(t2.parentTweet);
		return t2.parentTweet == null;
	}

}
