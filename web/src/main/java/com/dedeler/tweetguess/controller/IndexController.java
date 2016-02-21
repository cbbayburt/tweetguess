package com.dedeler.tweetguess.controller;

import com.dedeler.tweetguess.model.*;
import com.dedeler.tweetguess.service.CategoryService;
import com.dedeler.tweetguess.service.GameService;
import com.dedeler.tweetguess.service.LanguageService;
import com.dedeler.tweetguess.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;

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

    @ModelAttribute
    Game game() {
        return new Game();
    }

    @ModelAttribute
    User user() {
        return new User();
    }

    @ModelAttribute
    GamePreferences gamePreferences() {
        return new GamePreferences(null, new Language("en", "English", "production"));
    }

    @RequestMapping("/")
    public String index(Model model){
        model.addAttribute("questionsPerGame", GameService.QUESTIONS_PER_GAME);
        model.addAttribute("timeLimitMillis", GameService.QUESTION_TIME_LIMIT_MILLIS);
        return "index";
    }

    @RequestMapping("/initgame")
    @ResponseBody
    public LangCategory initGame(@RequestBody User user, @ModelAttribute GamePreferences prefs, Model model, HttpServletRequest request) {
        HttpSession session = request.getSession();
        if(!session.isNew()) {
            model.addAttribute(game());
        }

        userService.save(user);
        model.addAttribute(user);

        return new LangCategory(categoryService.getShuffledCategoriesByLang(prefs.getLang()), languageService.getLanguagesOrderByName());
    }

    @RequestMapping("/selectregion")
    @ResponseBody
    public List<Category> selectRegion(@RequestBody Language lang, @ModelAttribute GamePreferences prefs) {
        prefs.setLang(lang);

        return categoryService.getShuffledCategoriesByLang(prefs.getLang());
    }

    @RequestMapping("/getquestion")
    @ResponseBody
    public Question getQuestion(@RequestBody GamePreferences preferences, @ModelAttribute User user, @ModelAttribute Game game, Model model) {
        Question currentQuestion = game.getCurrentQuestion();

        // If no question is asked, create a game
        if(currentQuestion == null) {
            game = gameService.createNewGame(preferences, user);
            model.addAttribute(game);
        }

        Integer currentQuestionIndex = currentQuestion==null ? 0 : game.getQuestionList().indexOf(currentQuestion)+1;

        if(currentQuestionIndex == GameService.QUESTIONS_PER_GAME) {
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
        if(time > GameService.QUESTION_TIME_LIMIT_MILLIS)
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
        return (int)(GameService.QUESTION_MAX_SCORE - (time * GameService.QUESTION_MAX_SCORE / GameService.QUESTION_TIME_LIMIT_MILLIS));
    }

    @RequestMapping("/leaderboard")
    @ResponseBody
    public Leaderboard getLeaderboard(@ModelAttribute User user) {
        return gameService.getWeeklyLeaderboard(user);
    }

}
