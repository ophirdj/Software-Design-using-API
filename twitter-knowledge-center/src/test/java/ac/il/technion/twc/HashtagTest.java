package ac.il.technion.twc;

import org.junit.Before;
import org.junit.Test;

import java.util.Random;
import static ac.il.technion.twc.JsonTweetMaker.*;

import static org.junit.Assert.assertEquals;

/**
 * Created by Matan on 6/3/14.
 */
public class HashtagTest {

    static FuntionalityTester underInspection = new FuntionalityTester();

    static final int DB_SIZE = 4;
    String[] originalTweet = new String[1];
    String[] retweets = new String[DB_SIZE];
    String[] retweetsOfRetweet = new String[DB_SIZE];


    static final String TWEET1_ID = "having breakfast with my gorgeousesss!!! XOXO";
    static final String TWEET2_ID = "I love instagram!!! #love #instagram";
    static final String TWEET3_ID = "practicing my skydiving #yolo #sky";
    static final String TWEET4_ID = "breakfasts are so last jam XD";
    static final String TWEET5_ID = "broke my leg. how did this happen? FML";
    static final String TWEET6_ID = "back to full health. going parasailing #yolo";


    @Before
    public void setUp() throws Exception {
        underInspection.cleanPersistentData();
        originalTweet[0] = createOriginalTweetJson("1", TWEET2_ID);
        for (Integer i = 0; i < DB_SIZE; i++)
            retweets[i] = createRetweetJson(((Integer)(i+2)).toString(),originalTweet[0],((Integer)(i+10)).toString());

        for (Integer i = 0; i<DB_SIZE; i++)
            retweetsOfRetweet[i] = createRetweetJson(((Integer)(i+DB_SIZE+2)).toString(), retweets[3],i.toString());


        underInspection.importDataJson(originalTweet);
        underInspection.importDataJson(retweets);
        underInspection.importDataJson(retweetsOfRetweet);

        underInspection.setupIndex();




    }


    @Test(timeout=2/1000)
    public void testGetDailyHistogramRetweetCount(){
        assertEquals(((Integer)(DB_SIZE*2)).toString(), underInspection.getHashtagPopularity("love"));
        assertEquals(((Integer)(DB_SIZE*2)).toString(),underInspection.getHashtagPopularity("instagram"));

    }

    @Test(timeout=2/1000)
    public void testGetDailyHistogramOfRetweet(){
        assertEquals("0", underInspection.getHashtagPopularity("10"));
    }
}
