package ac.il.technion.twc.impl.queries;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import ac.il.technion.twc.api.Tweet;
import ac.il.technion.twc.api.TwitterQueryAPI;
import ac.il.technion.twc.api.visitor.Visitor;

/**
 * Finding the k-most coupled hashtags
 * 
 * @author Ziv Ronen
 * @date 17.06.2014
 * @mail akarks@gmail.com
 */
public class HashtagsHighestCouplingFinder implements Visitor {

  private List<String> coupledHashtags = Collections.<String> emptyList();

  @Override
  public void visit(final TwitterQueryAPI twitter) {
    final Map<CoupledTweets, Integer> map = new HashMap<>();
    for (final Tweet tweet : twitter.getTweets())
      for (int i = 0; i < tweet.getHashtags().size(); i++) {
        final String hashtag1 = tweet.getHashtags().get(i);
        for (final String hashtag2 : tweet.getHashtags().subList(i + 1,
            tweet.getHashtags().size() - 1)) {
          final CoupledTweets coupledTweets =
              new CoupledTweets(hashtag1, hashtag2);
          map.put(coupledTweets, Integer.valueOf(1 + (!map
              .containsKey(coupledTweets) ? 0 : map.get(coupledTweets)
              .intValue())));
        }
      }
    final List<Entry<CoupledTweets, Integer>> coupled =
        new ArrayList<>(map.entrySet());
    Collections.sort(coupled, new Comparator<Entry<CoupledTweets, Integer>>() {

      @Override
      public int compare(final Entry<CoupledTweets, Integer> firstEntry,
          final Entry<CoupledTweets, Integer> secondEntry) {
        return -1 * firstEntry.getValue().compareTo(secondEntry.getValue());
      }
    });
    coupledHashtags = new ArrayList<>();
    for (final Entry<CoupledTweets, Integer> entry : coupled)
      coupledHashtags.add(entry.getKey().returnFormat());
    coupledHashtags = Collections.unmodifiableList(coupledHashtags);
  }

  @Override
  public void clearData() {
    coupledHashtags = Collections.<String> emptyList();
  }

  /**
   * @param k
   *          the amount of coupled value to store
   * @return the k most coupled tweets
   */
  public String[] kMostCoupled(final int k) {
    return coupledHashtags.subList(0, k).toArray(new String[k]);
  }

  private static class CoupledTweets {
    private final String firstTweet;
    private final String secondTweet;

    public CoupledTweets(final String id1, final String id2) {
      if (0 > id1.compareTo(id2)) {
        firstTweet = id1;
        secondTweet = id2;
        return;
      }
      firstTweet = id2;
      secondTweet = id1;
    }

    public String returnFormat() {
      return new StringBuilder(firstTweet)
          .append(", ").append(secondTweet).toString(); //$NON-NLS-1$
    }

    @Override
    public int hashCode() {
      final int prime = 31;
      int result = 1;
      result =
          prime * result + (firstTweet == null ? 0 : firstTweet.hashCode());
      result =
          prime * result + (secondTweet == null ? 0 : secondTweet.hashCode());
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
      final CoupledTweets other = (CoupledTweets) obj;
      if (firstTweet == null) {
        if (other.firstTweet != null)
          return false;
      } else if (!firstTweet.equals(other.firstTweet))
        return false;
      if (secondTweet == null) {
        if (other.secondTweet != null)
          return false;
      } else if (!secondTweet.equals(other.secondTweet))
        return false;
      return true;
    }

  }

}
