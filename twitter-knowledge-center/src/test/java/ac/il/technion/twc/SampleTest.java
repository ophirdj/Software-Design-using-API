package ac.il.technion.twc;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class SampleTest {
  FuntionalityTester $ = new FuntionalityTester();

  @Before
  public void setup() throws Exception {
    $.cleanPersistentData();
  }

  @Test
  public void sampleTest() throws Exception {
    String[] lines =
        new String[] {
            "04/04/2014 12:00:00, iddqd", "05/04/2014 12:00:00, idkfa, iddqd" }; //$NON-NLS-1$ //$NON-NLS-2$
    $.importData(lines);
    lines = new String[] { JSONTweetsMessages.getString("baseTweet2") }; //$NON-NLS-1$
    $.importDataJson(lines);
    $.setupIndex();
    assertEquals("86400000", $.getLifetimeOfTweets("iddqd")); //$NON-NLS-1$ //$NON-NLS-2$
    assertArrayEquals(new String[] {
        "1,0", "0,0", "0,0", "0,0", "0,0", "1,0", "1,1" }, //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$ //$NON-NLS-6$ //$NON-NLS-7$
        $.getDailyHistogram());

    lines = new String[] { JSONTweetsMessages.getString("baseTweet1") }; //$NON-NLS-1$
    $.importDataJson(lines);
    $.setupIndex();
    assertArrayEquals(new String[] {
        "2,1", "0,0", "0,0", "0,0", "0,0", "1,0", "1,1" }, //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$ //$NON-NLS-6$ //$NON-NLS-7$
        $.getDailyHistogram());
    assertArrayEquals(new String[] {
        "0,0", "0,0", "0,0", "0,0", "0,0", "1,0", "1,1" }, //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$ //$NON-NLS-6$ //$NON-NLS-7$
        $.getTemporalHistogram("04/04/2014 12:00:00", "05/04/2014 12:00:00")); //$NON-NLS-1$ //$NON-NLS-2$
    assertEquals("1", $.getHashtagPopularity("yolo")); //$NON-NLS-1$ //$NON-NLS-2$
    assertEquals("0", $.getHashtagPopularity("matam")); //$NON-NLS-1$ //$NON-NLS-2$
  }
}
