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
	private final String originalTweet;
	private final Date tweetDate;
	private final List<String> hashtags;
	private final Long user;

	public Tweet(final String id, final String origin, final Date date,
			final List<String> hashtags, final Long userID) {
		tweetId = id;
		tweetDate = date;
		originalTweet = origin;
		this.hashtags = Collections.unmodifiableList(hashtags);
		user = userID;
	}

	public String getTweetID() {
		return tweetId;
	}

	public String getOriginalTweet() {
		return originalTweet;
	}

	public boolean isRetweet() {
		return originalTweet != null;
	}

	public Date getDate() {
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

	public Long getUserID() {
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
			return t1.originalTweet.equals(t2.originalTweet);
		return t2.originalTweet == null;
	}

}
