package ac.il.technion.twc;

import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Assert time constraints of methods of {@link FuntionalityTester}.
 * 
 * @author Ophir De Jager
 * 
 */
public class TimeConstraintsTest {

  private static final int BASE_TWEETS = 322;
  private static final String[] lines = TestUtils.generateTweets(BASE_TWEETS,
      5, 3, 0);
  private static final int linesLengthApproximation = 50232;

  // imported data but didn't set up index
  private final FunctionalityTester tkcSetupIndex = new FunctionalityTester();

  /**
   * Set needed data
   * 
   * @throws Exception
   */
  @Before
  public void setUp() throws Exception {
    tkcSetupIndex.importDataJson(lines);
  }

  /**
   * Cleanup.
   * 
   * @throws Exception
   */
  @After
  public void tearDown() throws Exception {
    tkcSetupIndex.cleanPersistentData();
  }

  /**
   * Test method for {@link FuntionalityTester#setupIndex()}
   * 
   * @throws Exception
   */
  @Test
  public final void setupIndexActualTime() throws Exception {
    final long start = System.currentTimeMillis();
    tkcSetupIndex.setupIndex();
    final long end = System.currentTimeMillis();
    assertTrue("Took " + (end - start) + " millis instead of " + 10
        * lines.length / 1000, end - start <= 10 * lines.length / 1000);
  }

  /**
   * Test method for {@link FuntionalityTester#setupIndex()}
   * 
   * @throws Exception
   */
  @Test(timeout = (10 * linesLengthApproximation / 1000))
  public final void setupIndexShouldRun10000nsForEachTweet() throws Exception {
    tkcSetupIndex.setupIndex();
  }

}
