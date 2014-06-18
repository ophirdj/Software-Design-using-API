package ac.il.technion.twc;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class FunctionalityTesterSampleTest {

  FunctionalityTester $ = new FunctionalityTester();

  @Before
  public void setup() {
    $.cleanPersistentData();
  }

  @After
  public void tearDown() {
    $.cleanPersistentData();
  }

  // @Test
  // public final void sampleTest() throws Exception {
  // $.importDataJson(new String[] { JSONTweetsMessages.getString("Sample1") });
  // $.setupIndex();
  // }

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

}
