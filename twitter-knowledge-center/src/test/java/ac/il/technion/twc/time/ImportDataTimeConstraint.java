package ac.il.technion.twc.time;

import org.junit.After;
import org.junit.Test;

import ac.il.technion.twc.FunctionalityTester;

/**
 * Assert time constraints of methods of {@link FunctionalityTester}.
 * 
 * @author Ophir De Jager
 * 
 */
public class ImportDataTimeConstraint {

  private static final int BASE_TWEETS = 2000;
  private static final String[] lines = TestUtils.generateTweets(BASE_TWEETS,
      5, 3, 0, 0);
  private static final int linesLengthApproximation = 312000;

  // didn't import data
  private final FunctionalityTester tkcImportData = new FunctionalityTester();

  /**
   * Cleanup.
   * 
   * @throws Exception
   */
  @After
  public void tearDown() throws Exception {
    tkcImportData.cleanPersistentData();
  }

  /**
   * Test method for {@link FunctionalityTester#importDataJson(String[])}
   * 
   * @throws Exception
   */
  @Test(timeout = (2 * linesLengthApproximation))
  public final void importDataShouldRun2msForEachTweet() throws Exception {
    tkcImportData.importDataJson(lines);
  }

}
