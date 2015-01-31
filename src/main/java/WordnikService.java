import com.wordnik.client.common.ApiException;
import com.wordnik.client.common.ApiInvoker;
import com.wordnik.client.model.Definition;

import java.util.*;

public class WordnikService {

  private final Properties properties;
  private final ApiInvoker apiInvoker = ApiInvoker.getInstance();

  public WordnikService(Properties properties) {
    this.properties = properties;
  }

  public List<Definition> getDefinitions(String word, String partOfSpeech, String sourceDictionaries, Integer limit, String includeRelated, String useCanonical, String includeTags, String apiKey) throws ApiException {
    // create path and map variables
    String path = "/v4/word.{format}/{word}/definitions".replaceAll("\\{format\\}", "json").replaceAll("\\{" + "word" + "\\}", apiInvoker.escapeString(word));

    // query params
    Map<String, String> queryParams = new HashMap<String, String>();
    Map<String, String> headerParams = new HashMap<String, String>();

    // verify required params are set
    if (word == null) {
      throw new ApiException(400, "missing required params");
    }
    if (!"null".equals(String.valueOf(limit)))
      queryParams.put("limit", String.valueOf(limit));
    if (!"null".equals(String.valueOf(partOfSpeech)))
      queryParams.put("partOfSpeech", String.valueOf(partOfSpeech));
    if (!"null".equals(String.valueOf(includeRelated)))
      queryParams.put("includeRelated", String.valueOf(includeRelated));
    if (!"null".equals(String.valueOf(sourceDictionaries)))
      queryParams.put("sourceDictionaries", String.valueOf(sourceDictionaries));
    if (!"null".equals(String.valueOf(useCanonical)))
      queryParams.put("useCanonical", String.valueOf(useCanonical));
    if (!"null".equals(String.valueOf(includeTags)))
      queryParams.put("includeTags", String.valueOf(includeTags));
    if (!"null".equals(String.valueOf(apiKey)))
      queryParams.put("api_key", String.valueOf(apiKey));
    try {
      String response = apiInvoker.getInstance().invokeAPI("http://api.wordnik.com", path, "GET", queryParams, null, headerParams);
      if (response != null) {
        return (List<Definition>) ApiInvoker.deserialize(response, "List", Definition.class);
      } else {
        return Collections.emptyList();
      }
    } catch (Exception ex) {
      return Collections.emptyList();
    }
  }

  public List<Definition> getDefinitions(String word) throws ApiException {
    List<Definition> list;
    String apiKey = properties.getProperty("apiKey");
    list = getDefinitions(word.toLowerCase(), null, null, null, null, null, null, apiKey);
    if (list.size() == 0) {
      list = getDefinitions(word, null, null, null, null, null, null, apiKey);
    }
    return list;
  }
}
