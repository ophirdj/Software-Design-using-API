package ac.il.technion.twc.api.file_io;

import java.io.IOException;
import java.util.List;

/**
 * Created by Matan on 6/2/14.
 */
public interface WriterReader {

  /**
   * 
   * @param list
   *          - Receives a list of Strings to <b>append</b> to the persistent
   *          medium.
   */
  void write(List<String> list);

  /**
   * @return - A list of Strings that contain all the string objects saved to
   *         the persistent medium.
   * @throws IOException
   *           - Thrown if there is a problem reading from the persistent
   *           medium.
   */
  List<String> read() throws IOException;

  /**
   * Deletes the persistent medium
   */
  void clean();
}
