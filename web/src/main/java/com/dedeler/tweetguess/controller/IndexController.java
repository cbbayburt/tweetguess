package com.dedeler.tweetguess.controller;

import com.dedeler.tweetguess.config.LocaleCollection;
import com.dedeler.tweetguess.config.SerializableResourceBundleMessageSource;
import com.dedeler.tweetguess.model.*;
import com.dedeler.tweetguess.model.view.*;
import com.dedeler.tweetguess.service.CategoryService;
import com.dedeler.tweetguess.service.GameService;
import com.dedeler.tweetguess.service.LanguageService;
import com.dedeler.tweetguess.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Can Bulut Bayburt
 */
@SessionAttributes(types = {User.class, Game.class})
@Controller
public class IndexController {

    private Map<String, String> availableLocales;

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
    private SerializableResourceBundleMessageSource messageSource;

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

    @RequestMapping("/initprefs")
    @ResponseBody
    public List<LangCategories> initPreferences(@RequestBody User user, Model model) {
        if(StringUtils.isBlank(user.getUsername()))
            throw new UnauthorizedException();

        userService.save(user);
        model.addAttribute(user);
        model.addAttribute(new Game());

        List<Category> categoryList = categoryService.getAll();
        List<Language> languageList = languageService.getLanguagesOrderByName();
        List<LangCategories> langCategories = languageList.stream().map(l -> new LangCategories(l, categoryList.parallelStream().
                    filter(c -> c.getLanguageId().equals(l.getCode()))
                    .collect(Collectors.toList())))
                .collect(Collectors.toList());

        return langCategories;
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

    @RequestMapping("/language.json")
    @ResponseBody
    public Properties language() {
        Properties messages = new Properties();
        messageSource.getAllProperties(LocaleContextHolder.getLocale()).entrySet().parallelStream()
                .filter(p -> p.getKey().toString().startsWith("json."))
                .forEach(p -> messages.put(p.getKey().toString().substring(5), p.getValue()));

        return messages;
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
        if(availableLocales == null) {
            availableLocales = new HashMap<>();
            for (Locale l : localeCollection) {
                availableLocales.put(l.toString(), messageSource.getMessage("lang", null, l));
            }
        }

        return availableLocales;
    }

}
