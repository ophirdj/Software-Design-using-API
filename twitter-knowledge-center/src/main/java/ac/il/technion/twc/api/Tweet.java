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
    this.hashtags =
        hashtags == null ? Collections.<String> emptyList() : Collections
            .unmodifiableList(hashtags);
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
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + (hashtags == null ? 0 : hashtags.hashCode());
    result =
        prime * result + (parentTweet == null ? 0 : parentTweet.hashCode());
    result = prime * result + (tweetDate == null ? 0 : tweetDate.hashCode());
    result = prime * result + (tweetId == null ? 0 : tweetId.hashCode());
    result = prime * result + (user == null ? 0 : user.hashCode());
    return result;
  }

  @Override
  public boolean equals(final Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    final Tweet other = (Tweet) obj;
    if (hashtags == null) {
      if (other.hashtags != null)
        return false;
    } else if (!hashtags.equals(other.hashtags))
      return false;
    if (parentTweet == null) {
      if (other.parentTweet != null)
        return false;
    } else if (!parentTweet.equals(other.parentTweet))
      return false;
    if (tweetDate == null) {
      if (other.tweetDate != null)
        return false;
    } else if (!tweetDate.equals(other.tweetDate))
      return false;
    if (tweetId == null) {
      if (other.tweetId != null)
        return false;
    } else if (!tweetId.equals(other.tweetId))
      return false;
    if (user == null) {
      if (other.user != null)
        return false;
    } else if (!user.equals(other.user))
      return false;
    return true;
  }

  @Override
  public String toString() {
    return "Tweet [tweetId=" + tweetId + ", parentTweet=" + parentTweet
        + ", tweetDate=" + tweetDate + ", hashtags=" + hashtags + ", user="
        + user + "]";
  }

}
