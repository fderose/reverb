import twitter4j.*;
import twitter4j.conf.Configuration;
import twitter4j.conf.ConfigurationBuilder;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class TwitterService {

  private Configuration getConfiguration() throws IOException {
    Properties properties = new Properties();
    InputStream inputStream = getClass().getClassLoader().getResourceAsStream("oauth.properties");
    properties.load(inputStream);
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
