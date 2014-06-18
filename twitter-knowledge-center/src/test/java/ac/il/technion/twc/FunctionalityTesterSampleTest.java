package ac.il.technion.twc;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Small test to check that our implementation make sense
 * 
 * @author Ziv Ronen
 * @date 18.06.2014
 * @mail akarks@gmail.com
 */
public class FunctionalityTesterSampleTest {

  FunctionalityTester $ = new FunctionalityTester();

  /**
   * 
   */
  @Before
  public void setup() {
    $.cleanPersistentData();
  }

  /**
   * 
   */
  @After
  public void tearDown() {
    $.cleanPersistentData();
  }

  /**
   * Check each of the six method we support
   * 
   * @throws Exception
   */
  @Test
  public final void sampleTest1() throws Exception {
    $.importData(new String[] {
        "04/04/2014 12:00:00, 1000", "05/04/2014 12:00:00, 1001, 1000", "06/04/2014 12:00:00, 1002, 1001" }); //$NON-NLS-1$ //$NON-NLS-2$
    $.setupIndex();
    assertEquals("1001",
        $.findTweetInTime("04/04/2014 12:00:01", "05/04/2014 12:00:01"));
    assertEquals("1000", $.getOriginalTweetsId("1001"));
    assertEquals("2", $.numberOfRetweets("1000"));
    $.importDataJson(new String[] { JSONTweetsMessages.getString("Sample1"),
        JSONTweetsMessages.getString("Sample2"),
        JSONTweetsMessages.getString("Sample3") });
    $.setupIndex();
    assertEquals("255362623626", $.getFirstTweet("123"));
    assertEquals("2", $.numberTweetsByUser("123"));
    assertEquals("1", $.numberTweetsByUser("456"));
    assertArrayEquals(new String[] { "aaa,bbb", "bbb,ccc" },
        $.hashtagCoupling(2));
    assertArrayEquals(new String[] { "aaa,bbb" }, $.hashtagCoupling(1));
    assertArrayEquals(new String[] { "aaa,bbb", "bbb,ccc" },
        $.hashtagCoupling(3));
  }

  /**
   * The same as {@link FunctionalityTesterSampleTest#sampleTest1}, but switch
   * {@link FunctionalityTester} to simulate shutdown.
   * 
   * @throws Exception
   */
  @Test
  public final void sampleTest2() throws Exception {
    $.importData(new String[] {
        "04/04/2014 12:00:00, 1000", "05/04/2014 12:00:00, 1001, 1000", "06/04/2014 12:00:00, 1002, 1001" }); //$NON-NLS-1$ //$NON-NLS-2$
    $.importDataJson(new String[] { JSONTweetsMessages.getString("Sample1"),
        JSONTweetsMessages.getString("Sample2"),
        JSONTweetsMessages.getString("Sample3") });
    final FunctionalityTester persistanceCheck = new FunctionalityTester();
    persistanceCheck.setupIndex();
    assertEquals("1001", persistanceCheck.findTweetInTime(
        "04/04/2014 12:00:01", "05/04/2014 12:00:01"));
    assertEquals("1000", persistanceCheck.getOriginalTweetsId("1001"));
    assertEquals("2", persistanceCheck.numberOfRetweets("1000"));
    assertEquals("255362623626", persistanceCheck.getFirstTweet("123"));
    assertEquals("2", persistanceCheck.numberTweetsByUser("123"));
    assertEquals("1", persistanceCheck.numberTweetsByUser("456"));
    assertArrayEquals(new String[] { "aaa,bbb", "bbb,ccc" },
        persistanceCheck.hashtagCoupling(2));
    assertArrayEquals(new String[] { "aaa,bbb" },
        persistanceCheck.hashtagCoupling(1));
    assertArrayEquals(new String[] { "aaa,bbb", "bbb,ccc" },
        persistanceCheck.hashtagCoupling(3));
  }

}
