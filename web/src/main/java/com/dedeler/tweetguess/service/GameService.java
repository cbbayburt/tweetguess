package com.dedeler.tweetguess.service;

import com.dedeler.tweetguess.model.*;
import com.dedeler.tweetguess.repository.GameRepository;
import com.dedeler.tweetguess.repository.PersonRepository;
import com.dedeler.tweetguess.repository.QuestionRepository;
import com.dedeler.tweetguess.repository.TweetRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

/**
 * Created by Aykut on 21.02.2016.
 */
@Service
public class GameService {

    public static final long QUESTION_TIME_LIMIT_MILLIS = 10000l;
    public static final int QUESTION_MAX_SCORE = 500;
    public static final int QUESTIONS_PER_GAME = 3;
    public static final int LEADERBOARD_SIZE = 10;

    @Autowired
    private TweetRepository tweetRepository;

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private QuestionRepository questionRepository;

    public void save(Game game) {
        gameRepository.save(game);
    }

    public Game createNewGame(GamePreferences gamePreferences, User user) {
        List<Tweet> tweetList = tweetRepository.findFirst10ByCategory(gamePreferences.getCategory());

        List<Question> questionList = new ArrayList<>();
        Map<Integer, Integer> answerMap = new HashMap<>();
        for(int i=0; i<QUESTIONS_PER_GAME; i++) {
            List<Person> personList = personRepository.findTop3ByCategory(gamePreferences.getCategory());
            personList.add(tweetList.get(i).getPerson());
            Collections.shuffle(personList);
            Integer correctAnswerIndex = personList.indexOf(tweetList.get(i).getPerson());
            questionList.add(new Question(null, tweetList.get(i), i, personList, null));
            answerMap.put(i, correctAnswerIndex);
        }

        LocalDate firstDayOfWeek = LocalDateTime.now().with(DayOfWeek.MONDAY).toLocalDate();
        Game game = new Game(null, LocalDateTime.now(), null, user, gamePreferences.getCategory(), gamePreferences.getLang(), questionList, answerMap, null, 0, 0, firstDayOfWeek);
        questionRepository.save(questionList);
        gameRepository.save(game);

        return game;
    }

    public Leaderboard getWeeklyLeaderboard(User currentUser) {
        LocalDate firstDayOfWeek = LocalDateTime.now().with(DayOfWeek.MONDAY).toLocalDate();

        List<Object[]> weeklyScoreList = gameRepository.getWeeklyScoreList(firstDayOfWeek);

        Integer leaderBoardSize = Math.min(LEADERBOARD_SIZE, weeklyScoreList.size());
        List<Score> scores = new ArrayList<>();
        boolean isUserOnTopList = false;
        for(int i=0; i<leaderBoardSize; i++) {
            Object[] summedScore = weeklyScoreList.get(i);

            String username = ((User) summedScore[0]).getUsername();
            boolean isCurrentUser = (currentUser!=null&&StringUtils.isNotBlank(currentUser.getUsername())) ? currentUser.getUsername().equals(username) : false;
            if(isCurrentUser && !isUserOnTopList) {
                isUserOnTopList = true;
            }

            Score score = new Score(username, isCurrentUser, i, Integer.valueOf(summedScore[1].toString()), Integer.valueOf(summedScore[2].toString()));
            scores.add(score);
        }

        if(!isUserOnTopList && currentUser!=null && StringUtils.isNotBlank(currentUser.getUsername())) {
            for(int i=LEADERBOARD_SIZE; i<weeklyScoreList.size(); i++) {
                Object[] summedScore = weeklyScoreList.get(i);
                if(currentUser.getUsername().equals(((User) summedScore[0]).getUsername())) {
                    scores.add(new Score(currentUser.getUsername(), true, i, Integer.valueOf(summedScore[1].toString()), Integer.valueOf(summedScore[2].toString())));
                    break;
                }
            }
        }

        return new Leaderboard(scores, null, null);
    }

}
