package ac.il.technion.twc.api.file_io;

import ac.il.technion.twc.api.exceptions.ParsingErrorException;

import java.io.*;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by Matan on 6/2/14.
 */
public class FileWriterReader implements WriterReader {

    private File file;

    public FileWriterReader(String path){
        file = new File(path);
        try {
		    if (!file.exists())
				file.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }

    @Override
    public void write(List<String> list) throws ParsingErrorException {
    	try {
    		FileWriter printWriter = new FileWriter(file,true);
        	for (String string : list) 
				printWriter.write(string+"\n");
	        
	        printWriter.close();
    	} catch (IOException e) {
    		e.printStackTrace();
    	}
    }

    @Override
    public void clean() {
    	try {
	        file.delete();
	        file.createNewFile();
	    } catch (IOException e) {
			e.printStackTrace();
		}
    }

    @Override
    public List<String> read() throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
        List<String> list = new ArrayList<String>();
        while (bufferedReader.ready()){
            list.add(bufferedReader.readLine());
        }
        bufferedReader.close();
        return list;
    }
}
