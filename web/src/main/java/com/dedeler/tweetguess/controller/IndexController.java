package com.dedeler.tweetguess.controller;

import com.dedeler.tweetguess.model.*;
import com.dedeler.tweetguess.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
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

    @RequestMapping("/")
    public String index(){
        return "index";
    }

    @RequestMapping(value = "/initgame", method = RequestMethod.POST)
    @ResponseBody
    public User initGame(@RequestBody User user, Model model, HttpServletRequest request) {
        HttpSession session = request.getSession();
        if(!session.isNew()) {
            model.addAttribute(game());
        }
        return user;
    }

    @RequestMapping(value = "/getquestion", method = RequestMethod.POST)
    @ResponseBody
    public Question getQuestion(@RequestBody GamePreferences preferences, @ModelAttribute Game game, Model model) {
        Question currentQuestion = game.getCurrentQuestion();
        if(currentQuestion == null) {
            game = gameService.createGame(preferences);
            model.addAttribute(game);
        }

        Integer currentQuestionIndex = currentQuestion==null ? 0 : game.getQuestionList().indexOf(currentQuestion)+1;
        game.setCurrentQuestion(game.getQuestionList().get(currentQuestionIndex));
        return game.getCurrentQuestion();
    }

    @RequestMapping("/answer")
    @ResponseBody
    public AnswerResult answer(@RequestBody Answer answer, @ModelAttribute Game game) {
        return new AnswerResult(answer.getChoice(), game.getAnswerMap().get(game.getCurrentQuestion().getIndex()));
    }

    @RequestMapping("/leaderboard")
    @ResponseBody
    public Leaderboard getLeaderboard(@ModelAttribute GameStatus gameStatus, @ModelAttribute User user, @ModelAttribute GamePreferences prefs) {
        List<Score> ls = new ArrayList<>();
        ls.add(new Score("asdx", false, 1, 123, 4567));
        ls.add(new Score("wef", false, 2, 12, 4563));
        ls.add(new Score("sdfnd", false, 3, 21, 4112));
        ls.add(new Score("tntrjthr", false, 4, 34, 3644));
        ls.add(new Score("htejtr", false, 5, 24, 3278));
        ls.add(new Score(user.getUsername(), false, 6, gameStatus.getCorrectAnswers(), gameStatus.getScore()));

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
