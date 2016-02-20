package com.dedeler.tweetguess;

import com.dedeler.tweetguess.model.Language;
import com.dedeler.tweetguess.model.Person;
import com.dedeler.tweetguess.model.Tweet;
import com.dedeler.tweetguess.repository.CategoryRepository;
import com.dedeler.tweetguess.repository.LanguageRepository;
import com.dedeler.tweetguess.repository.PersonRepository;
import com.dedeler.tweetguess.repository.TweetRepository;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import twitter4j.*;
import twitter4j.api.HelpResources;

import javax.transaction.Transactional;

/**
 * Created by Aykut on 20.02.2016.
 */
@Component
public class AppRunner implements ApplicationRunner {

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
    @Transactional
    public void run(ApplicationArguments applicationArguments) throws Exception {
        for(HelpResources.Language tgtLanguage : tweetGuessTwitter.getLanguages()) {
            Language language = dozerBeanMapper.map(tgtLanguage, Language.class);
            languageRepository.save(language);
            for(Category tgtCategory : tweetGuessTwitter.getSuggestedUserCategories(language.getCode())) {
                com.dedeler.tweetguess.model.Category category = dozerBeanMapper.map(tgtCategory, com.dedeler.tweetguess.model.Category.class);
                category.setLanguageId(language.getCode());
                categoryRepository.save(category);
                for(User tgtUser : tweetGuessTwitter.getUserSuggestions(category.getSlug(), language.getCode())) {
                    Person person = dozerBeanMapper.map(tgtUser, Person.class);
                    person.setCategoryId(category.getSlug());
                    person.setLangId(language.getCode());
                    personRepository.save(person);
//                    Paging paging = new Paging();
//                    paging.count(200);
//                    for(int i=0; i<5; i++) {
//                        paging.setPage(i);
                        for(Status tgtStatus : tweetGuessTwitter.getUserTimeline(person.getId())) {
                            Tweet tweet = dozerBeanMapper.map(tgtStatus, Tweet.class);
                            tweet.setCategoryId(category.getSlug());
                            tweet.setLangId(language.getCode());
                            tweet.setPersonId(person.getId());
                            tweetRepository.save(tweet);
                        }
//                    }
                }
            }
        }
    }

}
