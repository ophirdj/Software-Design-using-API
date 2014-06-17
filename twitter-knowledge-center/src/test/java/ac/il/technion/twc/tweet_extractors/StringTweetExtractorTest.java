package ac.il.technion.twc.tweet_extractors;

import ac.il.technion.twc.api.Tweet;
import ac.il.technion.twc.api.tweet_extractors.StringTweetExtractor;

import org.junit.Before;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;

import static org.junit.Assert.*;

public class StringTweetExtractorTest {

    String s1 = "15/05/2013 13:08:07, 334611141902872576";
    String s2 = "15/05/2013 13:08:07, 334611141890285568, 334611004342280192";
    Date d1;
    Date d2;
    String id1;
    String id2;
    String originalID1;
    String originalID2;
    StringTweetExtractor stringTweetExtractor = new StringTweetExtractor();


    @Before
    public void setUp() throws Exception {
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        d1 = format.parse("15/05/2013 13:08:07");
        d2 = format.parse("15/05/2013 13:08:07");
        id1 = "334611141902872576";
        id2 = "334611141890285568";
        originalID1 = null;
        originalID2 = "334611004342280192";
    }

    @Test
    public void testExtractOriginalTweet() throws Exception {

        Tweet t1 = stringTweetExtractor.extractTweet(s1);

        assertEquals(t1.getParentTweet(),originalID1);
        assertFalse(t1.isRetweet());
        assertEquals(t1.getDate(),d1);
        assertEquals(t1.getDay(),d1.getDay());
        assertEquals(t1.getTime(),d1.getTime());
        assertEquals(Collections.EMPTY_LIST, t1.getHashtags());
        assertEquals(t1.getTweetID(),id1);
    }

    @Test
    public void testExtractRetweet() throws Exception {
        Tweet t2 = stringTweetExtractor.extractTweet(s2);


        assertEquals(t2.getParentTweet(),originalID2);
        assertTrue(t2.isRetweet());
        assertEquals(t2.getDate(),d2);
        assertEquals(t2.getDay(),d2.getDay());
        assertEquals(t2.getTime(),d2.getTime());
        assertEquals(Collections.EMPTY_LIST, t2.getHashtags());
        assertEquals(t2.getTweetID(),id2);

    }
}