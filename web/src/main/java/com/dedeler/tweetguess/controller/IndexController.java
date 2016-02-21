package com.dedeler.tweetguess.controller;

import com.dedeler.tweetguess.model.*;
import com.dedeler.tweetguess.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Can Bulut Bayburt
 */
@SessionAttributes(types = {User.class, Game.class})
@Controller
public class IndexController {

    @Autowired
    private GameService gameService;

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
    public String index(){
        return "index";
    }

    @RequestMapping("/initgame")
    @ResponseBody
    public LangCategory initGame(@RequestBody User user, @ModelAttribute GamePreferences prefs, Model model, HttpServletRequest request) {
        HttpSession session = request.getSession();
        if(!session.isNew()) {
            model.addAttribute(game());
        }

        //Get categories for lang: prefs.getLanguage

        model.addAttribute(user);
        return new LangCategory(Arrays.asList(
                new Category("music", "Music", 20, "en"),
                new Category("politics", "Politics", 20, "en"),
                new Category("celebs", "Celebrities", 20, "en"),
                new Category("movies", "Movies", 20, "en")),
                Arrays.asList(new Language("en", "EN", null), new Language("tr", "TR", null)));
    }

    @RequestMapping("/selectregion")
    public List<Category> selectRegion(@RequestBody Language lang, @ModelAttribute GamePreferences prefs) {
        prefs.setLang(lang);

        //Get categories for lang: lang
        return Arrays.asList(
                new Category("music", "Music", 20, "en"),
                new Category("politics", "Politics", 20, "en"),
                new Category("celebs", "Celebrities", 20, "en"),
                new Category("movies", "Movies", 20, "en"));
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
        if(currentQuestionIndex == 10) {
            //TODO: Persist score
            return new Question(-1, null, null, null);
        }
        currentQuestion = game.getQuestionList().get(currentQuestionIndex);
        currentQuestion.setStartTime(Instant.now().toEpochMilli());

        game.setCurrentQuestion(currentQuestion);
        return currentQuestion;
    }

    @RequestMapping("/answer")
    @ResponseBody
    public AnswerResult answer(@RequestBody Answer answer, @ModelAttribute Game game) {
        int correct = game.getAnswerMap().get(game.getCurrentQuestion().getIndex());
        if(answer.getChoice() == correct) {
            game.setCorrectAnswers(game.getCorrectAnswers() + 1);
            game.setScore(game.getScore() + 10);
        }
        return new AnswerResult(answer.getChoice(), correct);
    }

    @RequestMapping("/leaderboard")
    @ResponseBody
    public Leaderboard getLeaderboard(@ModelAttribute Game game, @ModelAttribute User user, @ModelAttribute GamePreferences prefs) {
        List<Score> ls = new ArrayList<>();
        ls.add(new Score("asdx", false, 1, 123, 4567));
        ls.add(new Score("wef", false, 2, 12, 4563));
        ls.add(new Score("sdfnd", false, 3, 21, 4112));
        ls.add(new Score("tntrjthr", false, 4, 34, 3644));
        ls.add(new Score("htejtr", false, 5, 24, 3278));
        ls.add(new Score(user.getUsername(), true, 6, game.getCorrectAnswers(), game.getScore()));

        return new Leaderboard(ls, prefs.getCategory(), prefs.getLang());
    }

    @RequestMapping("/p/{fragment}")
    public String getFragment(@PathVariable("fragment") String fragment) {
        return "partial/" + fragment + " :: content";
    }

    @RequestMapping("/game")
    public String game(Model model) { return "game"; }

    @RequestMapping("/loading")
    public void loading() {}

    @RequestMapping("/question")
    public void question() {}

}
