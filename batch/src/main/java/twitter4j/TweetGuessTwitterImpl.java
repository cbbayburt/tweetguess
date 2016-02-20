package twitter4j;

import twitter4j.auth.Authorization;
import twitter4j.conf.Configuration;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Created by Aykut on 20.02.2016.
 */
public class TweetGuessTwitterImpl extends TwitterImpl implements TweetGuessTwitter {

    public TweetGuessTwitterImpl(Configuration conf, Authorization auth) {
        super(conf, auth);
    }

    @Override
    public ResponseList<Category> getSuggestedUserCategories(String lang) throws TwitterException {
        return this.factory.createCategoryList(this.get(this.conf.getRestBaseURL() + "users/suggestions.json?lang=" + lang));
    }

    @Override
    public ResponseList<User> getUserSuggestions(String categorySlug, String lang) throws TwitterException {
        HttpResponse res;
        try {
            res = this.get(this.conf.getRestBaseURL() + "users/suggestions/" + URLEncoder.encode(categorySlug, "UTF-8") + ".json?lang=" + lang);
        } catch (UnsupportedEncodingException var4) {
            throw new RuntimeException(var4);
        }

        return this.factory.createUserListFromJSONArray_Users(res);
    }

    private HttpResponse get(String url) throws TwitterException {
        this.ensureAuthorizationEnabled();

        if(!this.conf.isMBeanEnabled()) {
            return this.http.get(url, null, this.auth, this);
        } else {
            HttpResponse response = null;
            long start = System.currentTimeMillis();
            boolean var11 = false;

            try {
                var11 = true;
                response = this.http.get(url, null, this.auth, this);
                var11 = false;
            } finally {
                if(var11) {
                    long elapsedTime1 = System.currentTimeMillis() - start;
                    TwitterAPIMonitor.getInstance().methodCalled(url, elapsedTime1, this.isOk(response));
                }
            }

            long elapsedTime = System.currentTimeMillis() - start;
            TwitterAPIMonitor.getInstance().methodCalled(url, elapsedTime, this.isOk(response));
            return response;
        }
    }

    private boolean isOk(HttpResponse response) {
        return response != null && response.getStatusCode() < 300;
    }

}
