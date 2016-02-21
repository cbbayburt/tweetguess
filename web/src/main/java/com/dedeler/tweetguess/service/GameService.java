package com.dedeler.tweetguess.service;

import com.dedeler.tweetguess.model.*;
import com.dedeler.tweetguess.repository.PersonRepository;
import com.dedeler.tweetguess.repository.TweetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by Aykut on 21.02.2016.
 */
@Service
public class GameService {

    @Autowired
    private TweetRepository tweetRepository;

    @Autowired
    private PersonRepository personRepository;

    public Game createGame(GamePreferences gamePreferences) {
        List<Tweet> tweetList = tweetRepository.findFirst10ByCategoryId(gamePreferences.getCategory().getSlug());
        List<Question> questionList = new ArrayList<>();
        Map<Integer, Integer> answerMap = new HashMap<>();
        for(int i=0; i<10; i++) {
            List<Person> personList = personRepository.findTop3ByCategoryId(gamePreferences.getCategory().getSlug());
            Person person = personRepository.findOne(tweetList.get(i).getPersonId());
            personList.add(person);
            Collections.shuffle(personList);
            Integer correctAnswerIndex = personList.indexOf(person);
            questionList.add(new Question(i, tweetList.get(i).getText(), personList, null));
            answerMap.put(i, correctAnswerIndex);
        }
        return new Game(questionList, answerMap, null, 0, 0);
    }

}
