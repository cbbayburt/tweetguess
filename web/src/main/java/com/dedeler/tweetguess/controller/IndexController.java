package com.dedeler.tweetguess.controller;

import com.dedeler.tweetguess.model.*;
import com.dedeler.tweetguess.service.CategoryService;
import com.dedeler.tweetguess.service.GameService;
import com.dedeler.tweetguess.service.LanguageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Can Bulut Bayburt
 */
@SessionAttributes(types = {User.class, Game.class})
@Controller
public class IndexController {

    public static final long QUESTION_TIME_LIMIT_MILLIS = 10000l;
    public static final int QUESTION_MAX_SCORE = 500;
    public static final int QUESTIONS_PER_GAME = 3;

    @Autowired
    private GameService gameService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private LanguageService languageService;

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
        return new GamePreferences(null, new Language("en", "EN", "production"));
    }

    @RequestMapping("/")
    public String index(Model model){
        model.addAttribute("questionsPerGame", QUESTIONS_PER_GAME);
        model.addAttribute("timeLimitMillis", QUESTION_TIME_LIMIT_MILLIS);
        return "index";
    }

    @RequestMapping("/initgame")
    @ResponseBody
    public LangCategory initGame(@RequestBody User user, @ModelAttribute GamePreferences prefs, Model model, HttpServletRequest request) {
        HttpSession session = request.getSession();
        if(!session.isNew()) {
            model.addAttribute(game());
        }

        model.addAttribute(user);
        return new LangCategory(categoryService.getShuffledCategoriesByLang(prefs.getLang()), languageService.getLanguagesOrderByName());
    }

    @RequestMapping("/selectregion")
    public List<Category> selectRegion(@RequestBody Language lang, @ModelAttribute GamePreferences prefs) {
        prefs.setLang(lang);

        return categoryService.getShuffledCategoriesByLang(prefs.getLang());
    }

    @RequestMapping("/getquestion")
    @ResponseBody
    public Question getQuestion(@RequestBody GamePreferences preferences, @ModelAttribute Game game, Model model) {
        Question currentQuestion = game.getCurrentQuestion();
        if(currentQuestion == null) {
            game = gameService.createGame(preferences);
            model.addAttribute(game);
        }

        Integer currentQuestionIndex = currentQuestion==null ? 0 : game.getQuestionList().indexOf(currentQuestion)+1;
        if(currentQuestionIndex == QUESTIONS_PER_GAME) {
            //TODO: Persist score
            return new Question(-1, null, null, null);
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
    public Leaderboard getLeaderboard(@ModelAttribute Game game, @ModelAttribute User user, @ModelAttribute GamePreferences prefs) {
        List<Score> ls = new ArrayList<>();
        ls.add(new Score("asdx", false, 1, 123, 4567));
        ls.add(new Score("wef", false, 2, 12, 4563));
        ls.add(new Score("sdfnd", false, 3, 21, 4112));
        ls.add(new Score(user.getUsername(), true, 4, game.getCorrectAnswers(), game.getScore()));
        ls.add(new Score("tntrjthr", false, 5, 34, 3644));
        ls.add(new Score("htejtr", false, 6, 24, 3278));

        return new Leaderboard(ls, prefs.getCategory(), prefs.getLang());
    }

}
