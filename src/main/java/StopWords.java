import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class StopWords {
  private final static Set<String> stopWords;

  static {
    Set<String> set = new HashSet<>();
    ClassLoader cl = Driver.class.getClassLoader();
    URL url = cl.getResource("stopwords.txt");
    BufferedReader rdr = null;
    try {
      rdr = new BufferedReader(new FileReader(new File(url.getFile())));
      String line;
      while (true) {
        line = rdr.readLine();
        if (line == null) {
          break;
        }
        set.add(line.toLowerCase());
      }
      stopWords = Collections.unmodifiableSet(set);
    } catch (Exception e) {
      e.printStackTrace();
      throw new RuntimeException(e);
    } finally {
      if (rdr != null) {
        try {
          rdr.close();
        } catch (IOException e) {
        }
      }
    }
  }

  public static Set<String> getStopWords() {
    return stopWords;
  }
}
