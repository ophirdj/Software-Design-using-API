package ac.il.technion.twc.acceptance;

import static org.junit.Assert.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import ac.il.technion.twc.FuntionalityTester;
import static ac.il.technion.twc.JsonTweetMaker.*;

public class acceptanceTests {
	FuntionalityTester underInspection = new FuntionalityTester();
	
	static final int DB_SIZE = 7*1000*4;
    static String[] textTestInput = new String[DB_SIZE];
    static String[] jsonTestInput = new String[DB_SIZE];
    static Date[] daysOfWeek = new Date[7];
    static final String[] HASHTAGS = {"yolo", "shit", "swag", "niggazBeLike"};
    static final SimpleDateFormat dateFormat = 
    		new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
    
    
    static private void createLargeTweetArray(){
    	for(Integer i=0 ; i<DB_SIZE ; i++){
    		String tag = HASHTAGS[(i%4)];
    		String line = createOriginalTweetJson(i.toString(), "#"+tag);
    		jsonTestInput[i] = line;
    	}
    	
    	for(Integer i=DB_SIZE ; i<DB_SIZE*2 ; i++){
    		
    		String line = dateFormat.format(daysOfWeek[i%7])+
    				", "+i.toString();
			Integer original = i-DB_SIZE;
			line=line+", "+(original).toString();
    		textTestInput[i-DB_SIZE] = line;
    	}
    	
    }
    
    @BeforeClass
    static public void readyTheDatabases(){
    	for(int i=0 ; i<7 ; i++)
    		daysOfWeek[i] = new Date(2000, 3, i);
    	createLargeTweetArray();
    }
    
    @Before
    public void insureIndependaceOfTests(){
    	underInspection.cleanPersistentData();
    }
    

	@Test(timeout = DB_SIZE*2 +DB_SIZE/100+(DB_SIZE+1)/1000)
	public void dataBaseShouldBeQuickAndAccurate() throws Exception{
		underInspection.importDataJson(jsonTestInput);
		underInspection.setupIndex();
		for(Integer i=0 ; i<DB_SIZE ; i++){
			assertEquals("0",underInspection.getLifetimeOfTweets(i.toString()));
		}
		assertArrayEquals(
			new String[]
					{((Integer)DB_SIZE).toString()+",0","0,0","0,0","0,0","0,0","0,0","0,0"}, 
			underInspection.getDailyHistogram());
	}
	
	@Test(timeout = (DB_SIZE*2 +DB_SIZE/100)*2+(1)/1000)
	public void noDifferenceBetweenInputTypes() throws Exception{
		underInspection.importDataJson(jsonTestInput);
		underInspection.setupIndex();
		underInspection.importData(textTestInput);
		underInspection.setupIndex();
		
		String d1 = dateFormat.format(new Date(0,0,0));
		String d2 = dateFormat.format(new Date(40000,0,0));
		
		assertArrayEquals(
				underInspection.getTemporalHistogram(d1,d2), 
				underInspection.getDailyHistogram());
	}
	
	@Test(timeout = (DB_SIZE*2 +DB_SIZE/100)*2+1000)
	public void hashTagPopularityShouldBeQuickAndIncludeEveryRetwet() throws Exception{
		underInspection.importDataJson(jsonTestInput);
		underInspection.setupIndex();
		underInspection.importData(textTestInput);
		underInspection.setupIndex();
		Integer expected = DB_SIZE/4;
		
		for( int i=0 ; i<4 ; i++)
			assertEquals(expected.toString(), underInspection.getHashtagPopularity(HASHTAGS[i]));
		
	}
}
