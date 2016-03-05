package com.dedeler.tweetguess.controller;

import com.dedeler.tweetguess.config.LocaleCollection;
import com.dedeler.tweetguess.model.*;
import com.dedeler.tweetguess.service.CategoryService;
import com.dedeler.tweetguess.service.GameService;
import com.dedeler.tweetguess.service.LanguageService;
import com.dedeler.tweetguess.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * @author Can Bulut Bayburt
 */
@SessionAttributes(types = {User.class, Game.class})
@Controller
public class IndexController {

    @Autowired
    private GameService gameService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private LanguageService languageService;

    @Autowired
    private UserService userService;

    @Autowired
    private LocaleCollection localeCollection;

    @Autowired
    private MessageSource messageSource;

    @Value("${tweetguess.questionPerGame}")
    private Integer QUESTIONS_PER_GAME;

    @Value("${tweetguess.questionMaxScore}")
    private Integer QUESTION_MAX_SCORE;

    @Value("${tweetguess.questionTimeLimitMilis}")
    private Integer QUESTION_TIME_LIMIT_MILLIS;

    @ModelAttribute
    Game game() {
        return new Game();
    }

    @ModelAttribute
    User user() {
        return new User();
    }

    @RequestMapping("/")
    public String index(Model model){
        model.addAttribute("questionsPerGame", QUESTIONS_PER_GAME);
        model.addAttribute("timeLimitMillis", QUESTION_TIME_LIMIT_MILLIS);
        model.addAttribute("locales", getLanguages());

        return "index";
    }

    @RequestMapping("/initgame")
    @ResponseBody
    public LangCategory initGame(@RequestBody UserPreferences userPrefs, Model model, HttpServletRequest request) {
        if(StringUtils.isEmpty(userPrefs.getUser().getUsername()))
            throw new UnauthorizedException();

        userService.save(userPrefs.getUser());
        model.addAttribute(userPrefs.getUser());
        model.addAttribute(new Game());

        return new LangCategory(categoryService.getShuffledCategoriesByLang(userPrefs.getPreferences().getLang()), languageService.getLanguagesOrderByName());
    }

    @RequestMapping("/selectregion")
    @ResponseBody
    public List<Category> selectRegion(@RequestBody Language lang, Model model) {
        return categoryService.getShuffledCategoriesByLang(lang);
    }

    @RequestMapping("/getquestion")
    @ResponseBody
    public Question getQuestion(@RequestBody GamePreferences preferences, @ModelAttribute User user, @ModelAttribute Game game, Model model) {
        Question currentQuestion = game.getCurrentQuestion();

        // If no question is asked, create a game
        if(currentQuestion == null) {
            game = gameService.createNewGame(preferences, user);
            model.addAttribute(game);
            model.addAttribute(preferences);
        }

        Integer currentQuestionIndex = currentQuestion==null ? 0 : game.getQuestionList().indexOf(currentQuestion)+1;

        if(currentQuestionIndex.equals(QUESTIONS_PER_GAME)) {
            game.setEndTime(LocalDateTime.now());
            gameService.save(game);
            return null;
        }
        currentQuestion = game.getQuestionList().get(currentQuestionIndex);
        game.setCurrentQuestion(currentQuestion);

        currentQuestion.setStartTime(Instant.now().toEpochMilli());
        return currentQuestion;
    }

    @RequestMapping("/answer")
    @ResponseBody
    public AnswerResult answer(@RequestBody Answer answer, @ModelAttribute Game game) {
        Long time = Instant.now().toEpochMilli() - game.getCurrentQuestion().getStartTime();
        if(time > QUESTION_TIME_LIMIT_MILLIS)
            return null;

        int correct = game.getAnswerMap().get(game.getCurrentQuestion().getIndex());

        int score = 0;
        if(answer.getChoice() == correct) {
            score = calculateScore(time);
            game.setCorrectAnswers(game.getCorrectAnswers() + 1);
            game.setScore(game.getScore() + score);
        }
        return new AnswerResult(answer.getChoice(), correct, score);
    }

    private int calculateScore(long time) {
        return (int)(QUESTION_MAX_SCORE - (time * QUESTION_MAX_SCORE / QUESTION_TIME_LIMIT_MILLIS));
    }

    @RequestMapping("/leaderboard")
    @ResponseBody
    public Leaderboard getLeaderboard(@ModelAttribute User user) {
        return gameService.getWeeklyLeaderboard(user);
    }

    private Map<String, String> getLanguages() {
        Map<String, String> map = new HashMap<>();
        for(Locale l : localeCollection) {
            map.put(l.toString(), messageSource.getMessage("lang", null, l));
        }

        return map;
    }

}
