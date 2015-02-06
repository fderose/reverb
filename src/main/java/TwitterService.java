import twitter4j.*;
import twitter4j.conf.Configuration;
import twitter4j.conf.ConfigurationBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class TwitterService {

  private final Properties properties;

  public TwitterService(Properties properties) {
    this.properties = properties;
  }

  private Configuration getConfiguration() throws IOException {
    /**
     * see https://apps.twitter.com/app/7856593/show
     */

    ConfigurationBuilder cb = new ConfigurationBuilder();
    cb.setDebugEnabled(true)
      .setOAuthConsumerKey(properties.getProperty("OAuthConsumerKey"))
      .setOAuthConsumerSecret(properties.getProperty("OAuthConsumerSecret"))
      .setOAuthAccessToken(properties.getProperty("OAuthAccessToken"))
      .setOAuthAccessTokenSecret(properties.getProperty("OAuthAccessTokenSecret"));
    return cb.build();
  }

  public List<String> getTweetsForUser(String user) throws TwitterException, IOException {
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
