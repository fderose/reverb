import twitter4j.*;
import twitter4j.conf.Configuration;
import twitter4j.conf.ConfigurationBuilder;

import java.util.ArrayList;
import java.util.List;

public class TwitterService {

  private Configuration getConfiguration() {
    ConfigurationBuilder cb = new ConfigurationBuilder();
    cb.setDebugEnabled(true)
      .setOAuthConsumerKey("SqZEsLUe5NkxNy42F5ajKqbOI")
      .setOAuthConsumerSecret("4jQlfZbwmwFjb8hmCv25WIAwLHKwqiY6KXrOojJmelF7bOUEug")
      .setOAuthAccessToken("2481329784-UT4MNjToc3OEqUH7XfTNr8GwdXUCBxaS2hQHzPv")
      .setOAuthAccessTokenSecret("GZTfmD4ojk2uMkl0piodb2XSpHyjiNJZGGv209yZX00Dc");
    return cb.build();
  }

  public List<String> getTweetsForUser(String user) throws TwitterException {
    List<String> tweets = new ArrayList<>();
    Twitter twitter = new TwitterFactory(getConfiguration()).getInstance();
    Query query = new Query(user);
    QueryResult result = twitter.search(query);
    for (Status status : result.getTweets()) {
      tweets.add(status.getText());
    }
    return tweets;
  }
}
