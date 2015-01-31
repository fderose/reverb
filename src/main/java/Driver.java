import com.wordnik.client.common.ApiException;
import com.wordnik.client.model.Definition;
import twitter4j.TwitterException;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.stream.Collectors;

public class Driver {

  public static void main(String[] args) throws TwitterException, ApiException, IOException {

    if (args.length != 1) {
      System.out.println("Usage: Driver <username>");
      System.exit(-1);
    }
    String username = args[0];

    Properties properties = new Properties();
    InputStream inputStream = Driver.class.getClassLoader().getResourceAsStream("app.properties");
    properties.load(inputStream);

    TwitterService twitterService = new TwitterService(properties);
    WordnikService wordnikService = new WordnikService(properties);
    SortedMap<String, List<Definition>> definitionMap = new TreeMap<>(new WordComparator());

    for (String tweet : twitterService.getTweetsForUser(username)) {
      List<String> tweetWords = Arrays.asList(tweet.split("[ \n\t]+"));
      List<String> filteredWords = tweetWords.stream().
        filter(w -> !StopWords.getStopWords().contains(w.toLowerCase()) && !w.startsWith("http://") && !definitionMap.keySet().contains(w)).
        map(w -> w.replaceAll("[.,?!#@:]", "")).
        filter(w -> !w.isEmpty()).
        collect(Collectors.toList());
      for (String tweetWord : filteredWords) {
        definitionMap.put(tweetWord, wordnikService.getDefinitions(tweetWord));
      }
    }

    StringBuilder sb = new StringBuilder();
    boolean first = true;
    for (String word : definitionMap.keySet()) {
      if (first) {
        first = false;
      } else {
        sb.append("\n");
      }
      sb.append(word + ": ");
      sb.append(definitionMap.get(word).stream().map(Definition::getText).collect(Collectors.joining(" ")));
    }
    System.out.println(sb.toString());
  }
}
