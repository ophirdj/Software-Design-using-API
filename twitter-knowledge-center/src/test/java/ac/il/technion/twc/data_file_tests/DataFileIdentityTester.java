package ac.il.technion.twc.data_file_tests;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import java.io.BufferedReader;
import java.io.FileReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import ac.il.technion.twc.FuntionalityTester;

public class DataFileIdentityTester {
	static FuntionalityTester underInspection = 
    		new FuntionalityTester();
    
    static final int SMALL_FILE_SIZE = 90;
	static final String TEXT_INPUT_PATH = "src/test/resources/small_sample.txt";
    static final String JSON_INPUT_PATH = "src/test/resources/small_sample.json";
    static String[] smallFileInput = new String[SMALL_FILE_SIZE];
    static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
    static String[] textHistogram;
    static String[] jsonHistogram;
    
    
	static private void readTweetFileToMemory(int fileSize, String path, 
    		String[] lineArray) throws Exception{
    	
    	BufferedReader lineReader = new BufferedReader(new FileReader(path));
    	for(int i=0 ; i<fileSize ; i++){
    		String line = lineReader.readLine();
    		if(line== null)
    			break;
    		lineArray[i] = line;
    	}
    	lineReader.close();
    }
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		underInspection.cleanPersistentData();
    	readTweetFileToMemory(SMALL_FILE_SIZE, TEXT_INPUT_PATH, smallFileInput);
    	underInspection.importData(smallFileInput);
    	underInspection.setupIndex();
    	textHistogram = underInspection.getDailyHistogram();
    	
    	underInspection.cleanPersistentData();
    	underInspection = new FuntionalityTester();
    	readTweetFileToMemory(SMALL_FILE_SIZE, JSON_INPUT_PATH, smallFileInput);
    	underInspection.importDataJson(smallFileInput);
    	underInspection.setupIndex();
    	jsonHistogram = underInspection.getDailyHistogram();
    	
	}

	@AfterClass
	public static void clearTheMemoryFile(){
		underInspection.cleanPersistentData();
	}
    
    @Test
    public void bothInputFilesContainSameTweets() throws Exception {
    	assertArrayEquals(textHistogram,jsonHistogram);
    }
    
    @Test
    public void histogramTotalContentsShouldBeAsLargeAsFile() throws Exception{
    	int count =0;
    	for(int i=0 ; i<7 ; i++){
    		String[] today = textHistogram[i].split(",");
    		count += Integer.parseInt(today[0]);
    	}
    	assertEquals(SMALL_FILE_SIZE, count);
    }

    @Test
    public void temporalHistogramShouldContainAppropriateTweets() throws Exception{
    	String[] tmpHist = underInspection.getTemporalHistogram(
    			dateFormat.format(new Date(0, 0, 0)), 
    			dateFormat.format(new Date(40000, 0, 0)));
    	
    	assertArrayEquals(textHistogram, tmpHist);
    	
    	tmpHist = underInspection.getTemporalHistogram(
    			dateFormat.format(new Date(0, 0, 0)), 
    			dateFormat.format(new Date(0, 0, 0)));
    	
    	assertArrayEquals(new String[]{"0,0", "0,0", "0,0", "0,0", "0,0", "0,0", "0,0"},
    			tmpHist);
    }
}
