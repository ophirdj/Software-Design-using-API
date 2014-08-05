package ac.il.technion.twc.api.file_io;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import ac.il.technion.twc.api.exceptions.ParsingErrorException;

/**
 * Created by Matan on 6/2/14.
 */
public class FileWriterReader implements WriterReader {

  private final File file;

  /**
   * @param path
   */
  public FileWriterReader(final String path) {
    file = new File(path);
    try {
	  file.getParentFile().mkdirs();
      if (!file.exists())
        file.createNewFile();
    } catch (final IOException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void write(final List<String> list) throws ParsingErrorException {
    try (FileWriter printWriter = new FileWriter(file, true)) {
      for (final String string : list)
        printWriter.write(string + "\n");

      printWriter.close();
    } catch (final IOException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void clean() {
    try {
      file.delete();
      file.createNewFile();
    } catch (final IOException e) {
      e.printStackTrace();
    }
  }

  @Override
  public List<String> read() throws IOException {
    try (final BufferedReader bufferedReader =
        new BufferedReader(new FileReader(file))) {
      final List<String> list = new ArrayList<>();
      while (bufferedReader.ready())
        list.add(bufferedReader.readLine());
      bufferedReader.close();
      return list;
    }
  }
}
