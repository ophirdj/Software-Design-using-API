package ac.il.technion.twc.tweet_extractors;

import ac.il.technion.twc.api.Tweet;
import ac.il.technion.twc.api.tweet_extractors.JsonTweetExtractor;
import ac.il.technion.twc.api.tweet_extractors.StringTweetExtractor;
import static ac.il.technion.twc.JsonTweetMaker.*;

import org.junit.Before;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;

import static org.junit.Assert.*;

public class JsonTweetExtractorTest {
	static JsonTweetExtractor underInspection = new JsonTweetExtractor();
	static SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

    static String id1 = "334611141902872576";
    static String id2 = "334611141890285568";
    static String id3 = "334611141789028556";
    static String json1 = createOriginalTweetJson(id1, "my only friends are glass fish. #friends #fish #ocean");
    static String json2 = createRetweetJson(id2, json1, "");
    static String json3 = createRetweetJson(id3, json1, "");
    
    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void extractionOfTweetShouldExtractAllData() throws Exception {

        Tweet t = underInspection.extractTweet(json1);

        assertEquals(t.getTweetID(),id1);
        assertFalse(t.isRetweet());
        assertNull(t.getOriginalTweet());
        
        assertNotNull(t.getDate()); // I can't reason with this cursed date format.
        assertNotNull(t.getDay()); // but at least I can check that it's interpreted somehow.
        assertNotNull(t.getTime());
        
        assertEquals(3, t.getHashtags().size());
        assertTrue(t.getHashtags().contains("friends"));
        assertTrue(t.getHashtags().contains("fish"));
        assertTrue(t.getHashtags().contains("ocean"));
        
    }

    @Test
    public void testExtractRetweet() throws Exception {
        Tweet t = underInspection.extractTweet(json2);

        assertEquals(t.getTweetID(),id2);
        assertEquals(t.getOriginalTweet(), id1);
        assertTrue(t.isRetweet());
        assertNotNull(t.getDate());
        assertNotNull(t.getDay()); 
        assertNotNull(t.getTime());
        assertEquals(Collections.EMPTY_LIST, t.getHashtags());
        
        t = underInspection.extractTweet(json3);

        assertEquals(t.getTweetID(),id3);
        assertEquals(t.getOriginalTweet(), id1);
        assertTrue(t.isRetweet());
        assertNotNull(t.getDate());
        assertNotNull(t.getDay()); 
        assertNotNull(t.getTime());
        assertEquals(Collections.EMPTY_LIST, t.getHashtags());

    }
}