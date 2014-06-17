package ac.il.technion.twc.file_io;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ac.il.technion.twc.api.file_io.FileWriterReader;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class FileWriterReaderTest {
    String path = "src/test/resources/file_writer_test";
    FileWriterReader fileWriterReader = new FileWriterReader(path);

    @Before
    public void setup(){
        fileWriterReader.clean();
    }

    @Test
    public void testWrittenAndReadAreEqual() throws Exception {
        List<String> toWrite = new ArrayList<String>();
        for (Integer i = 0; i<10; i++)
            toWrite.add("#"+i.toString());

        fileWriterReader.write(toWrite);

        List<String> readFromFile = fileWriterReader.read();

        assertEquals(readFromFile,toWrite);
    }
}