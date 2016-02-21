package com.dedeler.tweetguess;

import com.dedeler.tweetguess.model.Category;
import com.dedeler.tweetguess.model.Language;
import com.dedeler.tweetguess.model.Person;
import com.dedeler.tweetguess.model.Tweet;
import com.dedeler.tweetguess.repository.CategoryRepository;
import com.dedeler.tweetguess.repository.LanguageRepository;
import com.dedeler.tweetguess.repository.PersonRepository;
import com.dedeler.tweetguess.repository.TweetRepository;
import org.dozer.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import twitter4j.Status;
import twitter4j.TweetGuessTwitter;
import twitter4j.TwitterException;
import twitter4j.User;
import twitter4j.api.HelpResources;


/**
 * Created by Aykut on 20.02.2016.
 */
@Component
public class AppRunner implements ApplicationRunner {

    private static final Logger LOGGER = LoggerFactory.getLogger(AppRunner.class);

    @Autowired
    private TweetGuessTwitter tweetGuessTwitter;

    @Autowired
    private Mapper dozerBeanMapper;

    @Autowired
    private LanguageRepository languageRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private TweetRepository tweetRepository;

    @Override
    public void run(ApplicationArguments applicationArguments) throws Exception {
        handleLanguage();
    }

    private void handleLanguage() {
        boolean exceptionStatus = false;
        do {
            try {
                for(HelpResources.Language tgtLanguage : tweetGuessTwitter.getLanguages()) {
                    exceptionStatus = false;
                    Language language = dozerBeanMapper.map(tgtLanguage, Language.class);
                    languageRepository.save(language);
                    handleCategory(language);
                }
            } catch(TwitterException e) {
                exceptionStatus = true;
                LOGGER.warn("Error in handleLanguage");
                sleep();
            }
        } while(exceptionStatus);
    }

    private void handleCategory(Language language) {
        boolean exceptionStatus = false;
        do {
            try {
                for(twitter4j.Category tgtCategory : tweetGuessTwitter.getSuggestedUserCategories(language.getCode())) {
                    exceptionStatus = false;
                    Category savedCategory = categoryRepository.findBySlugAndNameAndSizeAndLanguage(tgtCategory.getSlug(), tgtCategory.getName(), tgtCategory.getSize(), language);
                    if(savedCategory == null) {
                        Category category = dozerBeanMapper.map(tgtCategory, Category.class);
                        category.setLanguage(language);
                        categoryRepository.save(category);
                        handlePerson(category);
                    }
                }
            } catch(TwitterException e) {
                exceptionStatus = true;
                LOGGER.warn("Error in handleCategory");
                sleep();
            }
        } while(exceptionStatus);
    }

    private void handlePerson(Category category) {
        boolean exceptionStatus = false;
        do {
            try {
                for(User tgtUser : tweetGuessTwitter.getUserSuggestions(category.getSlug(), category.getLanguage().getCode())) {
                    exceptionStatus = false;
                    Person person = dozerBeanMapper.map(tgtUser, Person.class);
                    person.setCategory(category);
                    person.setLanguage(category.getLanguage());
                    personRepository.save(person);
                    handleTweet(person);
                }
            } catch(TwitterException e) {
                exceptionStatus = true;
                LOGGER.warn("Error in handlePerson");
                sleep();
            }
        } while(exceptionStatus);
    }

    private void handleTweet(Person person) {
        boolean exceptionStatus = false;
        do {
            try {
                for(Status tgtStatus : tweetGuessTwitter.getUserTimeline(person.getId())) {
                    exceptionStatus = false;
                    Tweet tweet = dozerBeanMapper.map(tgtStatus, Tweet.class);
                    tweet.setCategory(person.getCategory());
                    tweet.setLanguage(person.getLanguage());
                    tweet.setPerson(person);
                    tweet.setValid(true);
                    tweetRepository.save(tweet);
                }
            } catch(TwitterException e) {
                exceptionStatus = true;
                LOGGER.warn("Error in handleTweet");
                sleep();
            }
        } while(exceptionStatus);
    }

    private void sleep() {
        try {
            //Sleep 5 minutes
            Thread.sleep(5*60*1000);
        } catch (InterruptedException ie) {
            LOGGER.error("Error in sleep");
        }
    }

}
