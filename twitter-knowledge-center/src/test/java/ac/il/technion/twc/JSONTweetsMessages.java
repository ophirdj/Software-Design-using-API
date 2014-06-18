package ac.il.technion.twc;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class JSONTweetsMessages {
  private static final String BUNDLE_NAME = "ac.il.technion.twc.jsontweets"; //$NON-NLS-1$

  private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle
      .getBundle(BUNDLE_NAME);

  private JSONTweetsMessages() {
  }

  public static String getString(String key) {
    try {
      return RESOURCE_BUNDLE.getString(key);
    } catch (MissingResourceException e) {
      return '!' + key + '!';
    }
  }
}
