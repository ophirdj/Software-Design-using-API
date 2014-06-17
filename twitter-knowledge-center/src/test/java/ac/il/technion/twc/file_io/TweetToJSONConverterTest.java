package ac.il.technion.twc.file_io;

import static org.junit.Assert.*;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import ac.il.technion.twc.api.Tweet;
import ac.il.technion.twc.api.parsers.TweetJSONToMemory;


public class TweetToJSONConverterTest {
	static final String fileLocation = "src\\main\\resources\\tweetsDataBase.json";
	static final String backupLocation = "src\\test\\resources\\tweetsTestBackup.json";
	static File originalDB, testDB; 
	static TweetJSONToMemory underInspection = new TweetJSONToMemory();
	static final int SIZE = 10;
	static List<Tweet> insertionList  = new ArrayList<Tweet>(SIZE);
	
	@BeforeClass
	public static void setUpBeforeClass(){
		underInspection.clear();
		for(Integer i=0 ; i<SIZE ; i++)
			insertionList.add(i, 
					new Tweet("tweet"+i.toString(), null, 
					new Date(1,1,1), Collections.EMPTY_LIST));
	}

	@AfterClass
	public static void tearDownAfterClass(){
//		originalDB.renameTo(new File(fileLocation));
	}
	
	@Before
	public void createDatabaseBeforeTest() {
		underInspection.clear();
	}

	@Test
	public void dataBaseShouldBeEmptyAtSetup() {
		List list = underInspection.recover();
		assertEquals(list.size(),0);
	}
	
	@Test
	public void dataBaseShouldBeEmptyAfterDestruction() {
		underInspection.reserve(insertionList);
		
		underInspection.clear();
		List list = underInspection.recover();
		assertEquals(list.size(),0);
	}
	
	@Test
	public void dataShouldRemainEvenIfParserIsReplaced() {
		underInspection.reserve(insertionList);
		originalDB = new File(fileLocation);
		
		underInspection = new TweetJSONToMemory();
		List<Tweet> extractionList = underInspection.recover();
		assertEquals(extractionList.size(),SIZE);
		
		for(Tweet t : insertionList){
			assertTrue(extractionList.contains(t));
		}
	}
}
