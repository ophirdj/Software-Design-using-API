package ac.il.technion.twc.api.parsers;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import ac.il.technion.twc.api.Tweet;
import ac.il.technion.twc.api.exceptions.ParsingErrorException;
import ac.il.technion.twc.api.file_io.FileWriterReader;
import ac.il.technion.twc.api.file_io.WriterReader;

/**
 * Write tweet to memory in Json format
 */
public class TweetJSONToMemory implements TweetToMemory {
  static final String ID_FIELD = "id_str";
  static final String DATE_FIELD = "created_at";
  static final String RETWEET_FIELD = "original_tweet";
  static final String HASHTAG_FIELD = "hashtags";
  static final String USER_FIELD = "user";
  static final String DELIMITER = "|";
  static final String JSON_PATH = "src/main/resources/tweetsDataBase.json";
  static final SimpleDateFormat dateFormat = new SimpleDateFormat(
      "dd/MM/yyyy HH:mm:ss");

  WriterReader fileHandler = new FileWriterReader(JSON_PATH);

  /**
   * @param t
   *          tweet
   * @return string that represent a tweet
   */
  public static String encode(final Tweet t) {
    return new JSONObject().put(ID_FIELD, t.getTweetID())
        .put(RETWEET_FIELD, t.getParentTweet())
        .put(DATE_FIELD, dateFormat.format(t.getDate()))
        .put(HASHTAG_FIELD, t.getHashtags()).put(USER_FIELD, t.getUserID())
        .toString();
  }

  /**
   * @param s
   *          string that represent a tweet
   * @return the tweet s represent
   */
  public static Tweet decode(final String s) {
    try {
      final List<String> hashTags = new LinkedList<>();

      final JSONObject obj = new JSONObject(s);

      final String id = obj.getString(ID_FIELD);
      final String origId =
          obj.has(RETWEET_FIELD) ? obj.getString(RETWEET_FIELD) : null;
      final Date date = dateFormat.parse(obj.getString(DATE_FIELD));

      final JSONArray htList = obj.getJSONArray(HASHTAG_FIELD);
      for (int i = 0; i < htList.length(); i++)
        hashTags.add(htList.getString(i));

      final String userId =
          obj.has(USER_FIELD) ? obj.getString(USER_FIELD) : null;
      return new Tweet(id, origId, date, hashTags, userId);
    } catch (final Exception e) {
      e.printStackTrace();
      throw new ParsingErrorException();
    }
  }

  @Override
  public void reserve(final List<Tweet> tweets) {
    final List<String> encodedTweets = new LinkedList<>();
    for (final Tweet t : tweets)
      encodedTweets.add(encode(t));
    try {
      fileHandler.write(encodedTweets);
    } catch (final ParsingErrorException e) {
      e.printStackTrace();
      throw new ParsingErrorException();
    }
  }

  @Override
  public List<Tweet> recover() {
    try {
      final List<Tweet> decodedTweets = new LinkedList<>();
      for (final String s : fileHandler.read())
        decodedTweets.add(decode(s));
      return decodedTweets;
    } catch (final IOException e) {
      e.printStackTrace();
      throw new ParsingErrorException();
    }
  }

  @Override
  public void clear() {
    fileHandler.clean();
  }

}
