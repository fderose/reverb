import java.util.Comparator;

public class WordComparator implements Comparator<String> {
  @Override
  public int compare(String s1, String s2) {
    int retval;
    s1 = s1.toLowerCase();
    s2 = s2.toLowerCase();
    if (s1.startsWith("q")) {
      if (s2.startsWith("q")) {
        retval = s1.compareTo(s2);
      } else {
        retval = -1;
      }
    } else if (s2.startsWith("q")) {
      if (s1.startsWith("q")) {
        retval = s1.compareTo(s2);
      } else {
        retval = 1;
      }
    } else {
      retval = s1.compareTo(s2);
    }
    return retval;
  }
}
