package com.dedeler.tweetguess.service;

import com.dedeler.tweetguess.model.*;
import com.dedeler.tweetguess.repository.GameRepository;
import com.dedeler.tweetguess.repository.PersonRepository;
import com.dedeler.tweetguess.repository.QuestionRepository;
import com.dedeler.tweetguess.repository.TweetRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

    @Value("${tweetguess.questionPerGame}")
    private Integer QUESTIONS_PER_GAME;

    @Value("${tweetguess.leaderBoardSize}")
    private Integer LEADERBOARD_SIZE;

    @Autowired
    private TweetRepository tweetRepository;

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private QuestionRepository questionRepository;

    public void save(Game game) {
        questionRepository.save(game.getQuestionList());
        gameRepository.save(game);
    }

    public Game createNewGame(GamePreferences gamePreferences, User user) {
        Integer tweetCount = tweetRepository.countByCategoryAndValid(gamePreferences.getCategory(), true);
        List<Tweet> tweetList = tweetRepository.getGeoRandomizedTweetsByCategoryAndValid(gamePreferences.getCategory().getId(), user.getUsername(), getRandomNumbers(tweetCount), true);
        List<Person> personList = personRepository.findByCategory(gamePreferences.getCategory());

        List<Question> questionList = new ArrayList<>();
        Map<Integer, Integer> answerMap = new HashMap<>();
        Random random = new Random();
        for(int i=0; i<QUESTIONS_PER_GAME; i++) {
            Person correctPerson = tweetList.get(i).getPerson();
            List<Person> choiceList = new ArrayList<>();
            choiceList.add(correctPerson);
            while(choiceList.size() < 4 && choiceList.size()!=personList.size()) {
                Integer index = random.nextInt(personList.size()-1);
                if(!choiceList.contains(personList.get(index))) {
                    choiceList.add(personList.get(index));
                }
            }

            Collections.shuffle(choiceList);
            Integer correctAnswerIndex = choiceList.indexOf(tweetList.get(i).getPerson());
            questionList.add(new Question(null, tweetList.get(i), i, choiceList, null));
            answerMap.put(i, correctAnswerIndex);
        }

        LocalDate firstDayOfWeek = LocalDateTime.now().with(DayOfWeek.MONDAY).toLocalDate();
        Game game = new Game(null, LocalDateTime.now(), null, user, gamePreferences.getCategory(), gamePreferences.getLang(), questionList, answerMap, null, 0, 0, firstDayOfWeek);

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
            boolean isCurrentUser = (currentUser != null && StringUtils.isNotBlank(currentUser.getUsername())) && currentUser.getUsername().equals(username);
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

    public static int getRandom(Random r, double geoSeed) {
        geoSeed /= 3;
        double p = 1.0 / ((int) geoSeed);
        return (int) (Math.ceil(Math.log(r.nextDouble()) / Math.log(1.0 - p)));
    }

    private List<Integer> getRandomNumbers(Integer maxNumber) {
        Random random = new Random();
        List<Integer> randomNumbers = new ArrayList<>();
        while(randomNumbers.size() != QUESTIONS_PER_GAME) {
            Integer num = getRandom(random, maxNumber);
            if(num <= maxNumber && !randomNumbers.contains(num)) {
                randomNumbers.add(num);
            }
        }
        return randomNumbers;
    }

}
