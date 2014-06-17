package ac.il.technion.twc.data_file_tests;

import java.io.BufferedReader;
import java.io.FileReader;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import ac.il.technion.twc.FuntionalityTester;

@Ignore
public class BigTextDataTester {
    static FuntionalityTester underInspection = 
    		new FuntionalityTester();
    
	static final int LARGE_FILE_SIZE = 89794;
    static final int SMALL_FILE_SIZE = 90;
	static final String BASIC_INPUT_PATH = "src/test/resources/large_sample.txt";
    static final String EXTRA_INPUT_PATH = "src/test/resources/small_sample.txt";
    static String[] bigFileInput = new String[LARGE_FILE_SIZE];
    static String[] smallFileInput = new String[SMALL_FILE_SIZE];
    
    
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
    	readTweetFileToMemory(LARGE_FILE_SIZE, BASIC_INPUT_PATH, bigFileInput);
    	readTweetFileToMemory(SMALL_FILE_SIZE, EXTRA_INPUT_PATH, smallFileInput);
	}

	@Before
	public void insureIndependenceOfTests(){
    	underInspection.cleanPersistentData();
	}
	
	@AfterClass
	public static void clearTheMemoryFile(){
		underInspection.cleanPersistentData();
	}
	
	@Test(timeout = LARGE_FILE_SIZE*2)
    public void importShouldBeQuick() throws Exception {
    	underInspection.importData(bigFileInput);
    }
    
    @Test(timeout = LARGE_FILE_SIZE*2+LARGE_FILE_SIZE*2/1000)
    public void setupIndexShouldBeQuick() throws Exception {
    	underInspection.importData(bigFileInput);
    	underInspection.setupIndex();
    }
    
    @Test(timeout=(LARGE_FILE_SIZE+SMALL_FILE_SIZE)*(2+2/1000))
    public void timeOfExtraImportShouldBeProportionalToEntireDataBaseSize() throws Exception{
    	underInspection.importData(bigFileInput);
    	underInspection.importData(smallFileInput);
    	underInspection.setupIndex();
    }

}
