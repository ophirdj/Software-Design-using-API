package ac.il.technion.twc;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;

import org.junit.Test;

import ac.il.technion.twc.api.Tweet;
import static org.junit.Assert.*;

/**
 * Created by Matan on 5/9/14.
 */
public class TweetTest {
	
	static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
	static final Date PAST = new Date(1984,4,1);
    static final Date FUTURE = new Date(2015,5,1);
    static final String TWEET1_ID = "having breakfast with my gorgeousesss!!! XOXO";
    static final String TWEET2_ID = "I love instagram!!! #love #instagram";

    private static final Tweet tweet = 
    		new Tweet(TWEET1_ID, null, PAST, Collections.EMPTY_LIST, null);
    private static final Tweet retweet = 
    		new Tweet(TWEET2_ID, TWEET1_ID, FUTURE, Collections.EMPTY_LIST, null);

    @Test
    public void testGetTweetID() throws Exception {
    	assertEquals(TWEET1_ID, tweet.getTweetID());
        assertEquals(TWEET2_ID, retweet.getTweetID());
    }

    @Test
    public void testGetOriginalTweetForRetweet() throws Exception {
        assertEquals(TWEET1_ID, retweet.getOriginalTweet());
    }

    @Test
    public void testGetOriginalTweetForOriginalReturnsNull() throws Exception {
        assertNull(tweet.getOriginalTweet());
    }

    @Test
    public void testIsRetweetForRetweetIsTrue() throws Exception {
        assertTrue(retweet.isRetweet());
    }

    @Test
    public void testIsRetweetForOriginalTweetIsFalse() throws Exception {
        assertFalse(tweet.isRetweet());
    }

    @Test
    public void getDateAndGetTimeShouldReturnExactlyTheSameDate() throws Exception {
    	assertEquals(PAST, tweet.getDate());
    	assertEquals(FUTURE, retweet.getDate());
    	
    	assertEquals(PAST.getTime(), tweet.getTime());
    	assertEquals(FUTURE.getTime(), retweet.getTime());
    }

}
