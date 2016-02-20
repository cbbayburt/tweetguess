package com.dedeler.tweetguess;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import twitter4j.Category;
import twitter4j.TweetGuessTwitter;
import twitter4j.User;
import twitter4j.api.HelpResources;

/**
 * Created by Aykut on 20.02.2016.
 */
@Component
public class AppRunner implements ApplicationRunner {

    @Autowired
    private TweetGuessTwitter tweetGuessTwitter;

    @Override
    public void run(ApplicationArguments applicationArguments) throws Exception {
        for(HelpResources.Language language : tweetGuessTwitter.getLanguages()) {
            for(Category category : tweetGuessTwitter.getSuggestedUserCategories(language.getCode())) {
                for(User user : tweetGuessTwitter.getUserSuggestions(category.getSlug(), language.getCode())) {
                    System.out.println(user.getName());
                }
            }
        }
    }

}
