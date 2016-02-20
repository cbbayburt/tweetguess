package twitter4j;

/**
 * Created by Aykut on 20.02.2016.
 */
public interface TweetGuessTwitter extends Twitter {

    ResponseList<Category> getSuggestedUserCategories(String lang) throws TwitterException;
    ResponseList<User> getUserSuggestions(String slug, String lang) throws TwitterException;

}
