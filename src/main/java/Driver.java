import com.wordnik.client.common.ApiException;
import com.wordnik.client.model.Definition;
import twitter4j.TwitterException;

import java.util.*;
import java.util.stream.Collectors;

public class Driver {

  public static void main(String[] args) throws TwitterException, ApiException {
    TwitterService twitterService = new TwitterService();
    WordnikService wordnikService = new WordnikService();
    SortedMap<String, List<Definition>> definitionMap = new TreeMap<>(new WordComparator());

    for (String tweet : twitterService.getTweetsForUser("fjderose")) {
      List<String> tweetWords = Arrays.asList(tweet.split(" "));
      List<String> filteredWords = tweetWords.stream().
        filter(w -> !StopWords.getStopWords().contains(w.toLowerCase())).
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
