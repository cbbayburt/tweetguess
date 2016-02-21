package com.dedeler.tweetguess.service;

import com.dedeler.tweetguess.model.*;
import com.dedeler.tweetguess.repository.GameRepository;
import com.dedeler.tweetguess.repository.PersonRepository;
import com.dedeler.tweetguess.repository.QuestionRepository;
import com.dedeler.tweetguess.repository.TweetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
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

    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private QuestionRepository questionRepository;

    public Game createNewGame(GamePreferences gamePreferences, User user) {
        List<Tweet> tweetList = tweetRepository.findFirst10ByCategory(gamePreferences.getCategory());

        List<Question> questionList = new ArrayList<>();
        Map<Integer, Integer> answerMap = new HashMap<>();
        for(int i=0; i<Game.QUESTIONS_PER_GAME; i++) {
            List<Person> personList = personRepository.findTop3ByCategory(gamePreferences.getCategory());
            personList.add(tweetList.get(i).getPerson());
            Collections.shuffle(personList);
            Integer correctAnswerIndex = personList.indexOf(tweetList.get(i).getPerson());
            questionList.add(new Question(null, tweetList.get(i), i, personList, null));
            answerMap.put(i, correctAnswerIndex);
        }

        Game game = new Game(null, LocalDateTime.now(), null, user, gamePreferences.getCategory(), gamePreferences.getLang(), questionList, answerMap, null, 0, 0);
        questionRepository.save(questionList);
        gameRepository.save(game);

        return game;
    }

}
