package ac.il.technion.twc.api.parsers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import ac.il.technion.twc.api.Tweet;
import ac.il.technion.twc.api.exceptions.ParsingErrorException;
import ac.il.technion.twc.api.file_io.FileWriterReader;
import ac.il.technion.twc.api.file_io.WriterReader;

/**
 * Custom made format for tweet. Faster to parse and recover
 * 
 * @author Ziv Ronen
 * @date 18.06.2014
 * @mail akarks@gmail.com
 * 
 * @version 2.1
 * @since 2.1
 */
public class TweetStringToMemory implements TweetToMemory {
  private static final String DELIMITER = "#";
  private static final String NULL_SIGN = "%";
  private static final String PATH = "src/main/resources/tweetsDataBase.txt";
  private static final String WRAPER = "_";

  WriterReader fileHandler = new FileWriterReader(PATH);

  static String encode(final Tweet t) {
    return new StringBuilder(t.getTweetID()).append(DELIMITER)
        .append(storeNullAble(t.getParentTweet())).append(DELIMITER)
        .append(t.getTime()).append(DELIMITER)
        .append(storeNullAble(t.getUserID())).append(DELIMITER)
        .append(encodeHashtags(t.getHashtags())).toString();
  }

  private static String storeNullAble(final String string) {
    return string == null ? NULL_SIGN : string;
  }

  private static String encodeHashtags(final List<String> hashtags) {
    if (hashtags == null || hashtags.isEmpty())
      return "";
    final StringBuilder builder = new StringBuilder();
    for (final String string : hashtags)
      builder.append(WRAPER).append(string).append(WRAPER).append(DELIMITER);
    return builder.toString();
  }

  static Tweet decode(final String s) {
    final String[] fields = s.split(DELIMITER);
    return new Tweet(fields[0], loadNullAble(fields[1]), new Date(Long.valueOf(
        fields[2]).longValue()), decodeHashtags(fields),
        loadNullAble(fields[3]));
  }

  private static String loadNullAble(final String string) {
    return string.equals(NULL_SIGN) ? null : string;
  }

  private static List<String> decodeHashtags(final String[] fields) {
    final List<String> $ = new ArrayList<>();
    for (int i = 4; i < fields.length; i++)
      $.add(fields[i].substring(1, fields[i].length() - 1));
    return $;
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
