package ac.il.technion.twc;

import static org.junit.Assert.*;
import static ac.il.technion.twc.JsonTweetMaker.createOriginalTweetJson;
import ac.il.technion.twc.api.exceptions.TweetNotFoundException;

import org.json.JSONObject;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

/**
 * Created by Matan on 5/7/14.
 */
//@Ignore
public class TheMillionTweetsTest {
    static FuntionalityTester underInspection = 
    		new FuntionalityTester();
    
    static final int HALF_DB_SIZE = 500000;
    static String[] textTestInput = new String[HALF_DB_SIZE];
    static String[] jsonTestInput = new String[HALF_DB_SIZE];
    static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
    static String[] additionalTweets = new String[6];
    static Date[] daysOfWeek = new Date[7];
    static final String TWEET1_ID = "having breakfast with my gorgeousesss!!! XOXO";
    static final String TWEET2_ID = "I love instagram!!! #love #instagram";
    static final String TWEET3_ID = "practicing my skydiving #yolo #sky";
    static final String TWEET4_ID = "breakfasts are so last jam XD";
    static final String TWEET5_ID = "broke my leg. how did this happen? FML";
    static final String TWEET6_ID = "back to full health. going parasailing #yolo";
    static final String[] HASHTAGS = {"#yolo", "#shit", "#swag", "#niggazBeLike"};
    
    static final Date LONG_AGO = new Date(1980,1,1);
    static final Date PAST = new Date(1984,4,1);
    static final Date FUTURE = new Date(2015,5,1);

    
    static private void createLargeTweetArray(){
    	Random rnd = new Random();
    	for(Integer i=0 ; i<HALF_DB_SIZE ; i++){
    		int dayNum = rnd.nextInt(7);
    		String line = dateFormat.format(daysOfWeek[dayNum])+
    				", "+i.toString();
    		if(i>(HALF_DB_SIZE*9)/10){
    			Integer original = rnd.nextInt((HALF_DB_SIZE*9)/10);
				line=line+", "+original.toString();
    		}
    		textTestInput[i] = line;
    	}
    	
    	for(Integer i=HALF_DB_SIZE ; i<HALF_DB_SIZE*2 ; i++){
    		String tag = HASHTAGS[rnd.nextInt(4)];
    		String line = createOriginalTweetJson(i.toString(), tag);
    		jsonTestInput[i-HALF_DB_SIZE] = line;
    	}
    }
    
    private static void createSmallTweetArray() {
    	additionalTweets = new String[6];
    	additionalTweets[0] = 
    			dateFormat.format(PAST)+", "+TWEET1_ID;
    	additionalTweets[1] = 
    			dateFormat.format(PAST)+", "+TWEET2_ID+", "+TWEET1_ID;
    	additionalTweets[2] = 
    			dateFormat.format(LONG_AGO)+", "+TWEET3_ID;
    	additionalTweets[3] = 
    			dateFormat.format(FUTURE)+", "+TWEET4_ID+", "+TWEET1_ID;
    	additionalTweets[4] = 
    			dateFormat.format(PAST)+", "+TWEET5_ID+", "+TWEET3_ID;
    	additionalTweets[5] = 
    			dateFormat.format(FUTURE)+", "+TWEET6_ID+", "+TWEET5_ID;
    }
    
    private void searchForEveryTweet() throws Exception {
    	for(Integer i=0 ; i<HALF_DB_SIZE ; i++)
    		underInspection.getLifetimeOfTweets(i.toString());
    }
    
    @BeforeClass
    static public void createTheKnowledgeCenter() throws Exception {
    	daysOfWeek[0] = new Date(1990,1,0);
    	daysOfWeek[1] = new Date(1990,1,1);
    	daysOfWeek[2] = new Date(1990,1,2);
    	daysOfWeek[3] = new Date(1990,1,3);
    	daysOfWeek[4] = new Date(1990,1,4);
    	daysOfWeek[5] = new Date(1990,1,5);
    	daysOfWeek[6] = new Date(1990,1,6);
    	
        createLargeTweetArray();
    	createSmallTweetArray();
    }
    
    @AfterClass
	public static void clearTheMemoryFile(){
		underInspection.cleanPersistentData();
	}
    
    @Before
    public void insureIndependenceOfTests() throws Exception {
    	underInspection.cleanPersistentData();
    	underInspection.importData(textTestInput);
    	underInspection.importDataJson(jsonTestInput);
    	underInspection.setupIndex();
    }

    @Test(timeout = HALF_DB_SIZE*4+HALF_DB_SIZE*10/1000)
    public void setupIndexShouldBeSuperFast() throws Exception {
    	underInspection.cleanPersistentData();
    	underInspection.importData(textTestInput);
    	underInspection.importDataJson(jsonTestInput);
    	underInspection.setupIndex();
    }
    
    @Test(timeout = HALF_DB_SIZE*4/1000)
    public void getLifetimeOfTweetsShouldBeQuickOnAvarage() throws Exception {
    	searchForEveryTweet();
    }
    
    @Test(timeout=2/1000)
    public void getDailyHistogramShouldBeQuick(){
    	underInspection.getDailyHistogram();
    }

    @Test
    public void importsFromAdditionalSourcesShouldAddAllNewIdsToDataBase() throws Exception{
    	underInspection.importData(additionalTweets);
    	underInspection.setupIndex();
    	
    	try {
	    	searchForEveryTweet();
	    	
	    	underInspection.getLifetimeOfTweets(TWEET1_ID);
	    	underInspection.getLifetimeOfTweets(TWEET2_ID);
	    	underInspection.getLifetimeOfTweets(TWEET3_ID);
	    	underInspection.getLifetimeOfTweets(TWEET4_ID);
	    	underInspection.getLifetimeOfTweets(TWEET5_ID);
	    	underInspection.getLifetimeOfTweets(TWEET6_ID);
    	} catch(TweetNotFoundException e) {
    		fail("from some reason, importing extra data harms integrity of data");
    	}
    }
    
    @Test
    public void persistentDataSurviveDeletionOfKnowledgeCenter() throws Exception{
    	underInspection = new FuntionalityTester();
    	underInspection.setupIndex();
    	
    	searchForEveryTweet();	
    }
    
    @Test(expected = TweetNotFoundException.class)
    public void persistentDataShouldBeErasedByClean() throws Exception{
    	underInspection.cleanPersistentData();
    	underInspection.setupIndex();
    	
    	underInspection.getLifetimeOfTweets("1");	
    }
    
    @Test
    public void lifetimeOfTweetShouldBeAccurate() throws Exception{
    	underInspection.importData(additionalTweets);
    	underInspection.setupIndex();
    	Long lifetime1 = (FUTURE.getTime() -PAST.getTime());
    	Long lifetime3 = (FUTURE.getTime() -LONG_AGO.getTime());
    	
    	assertEquals(underInspection.getLifetimeOfTweets(TWEET1_ID),lifetime1.toString());
    	assertEquals(underInspection.getLifetimeOfTweets(TWEET3_ID),lifetime3.toString());
    }
    
    @Test(timeout = 250)
    public void hashtagPopularityShouldBeQuick() throws Exception{
    	Random rnd = new Random();
    	String tag = HASHTAGS[rnd.nextInt(4)];
    	underInspection.getHashtagPopularity(tag);
    }
    
    @Test(timeout = 2000)
    public void temporalHistogramShouldBeDamnFast() throws Exception{
        underInspection.getTemporalHistogram(dateFormat.format(LONG_AGO), dateFormat.format(PAST));
    }
}