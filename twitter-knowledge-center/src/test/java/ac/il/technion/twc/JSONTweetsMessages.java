package ac.il.technion.twc;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * For reading json tweets from files
 * 
 * @author Ziv Ronen
 * @date 18.06.2014
 * @mail akarks@gmail.com
 */
public class JSONTweetsMessages {
  private static final String BUNDLE_NAME = "ac.il.technion.twc.jsontweets"; //$NON-NLS-1$

  private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle
      .getBundle(BUNDLE_NAME);

  private JSONTweetsMessages() {
  }

  /**
   * @param key
   *          the key for the tweet
   * @return a tweet in json format
   */
  public static String getString(final String key) {
    try {
      return RESOURCE_BUNDLE.getString(key);
    } catch (final MissingResourceException e) {
      return '!' + key + '!';
    }
  }
}
