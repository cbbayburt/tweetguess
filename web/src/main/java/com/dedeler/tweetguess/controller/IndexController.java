package com.dedeler.tweetguess.controller;

import com.dedeler.tweetguess.model.*;
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
@SessionAttributes(types = {User.class, GamePreferences.class, GameStatus.class})
@Controller
public class IndexController {

    @ModelAttribute
    GameStatus gameStatus() {
        return new GameStatus(-1, 0, 0);
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
            //Reset game status
            model.addAttribute(gameStatus());
        }
        return user;
    }

    @RequestMapping(value = "/getquestion", method = RequestMethod.POST)
    @ResponseBody
    public Question getQuestion(@RequestBody GamePreferences preferences, @ModelAttribute GameStatus status, Model model) {

        if(status.getCurrentQuestion() == 2) {
            //End game
            return new Question(0, -1, 0, null, null);
        }

        List<Person> lp = new ArrayList<>();
        lp.add(new Person(1l, "Aasd Zxc", "@aasdzxc", null));
        lp.add(new Person(2l, "Vbb Zxx", "@vbbzxx", null));
        lp.add(new Person(3l, "Qqwe Ads", "@qqweads", null));
        lp.add(new Person(4l, "Hjk Jk", "@hjkjk", null));

        model.addAttribute(preferences);
        status.setCurrentQuestion(status.getCurrentQuestion() + 1);
        return new Question(1, status.getCurrentQuestion(), 3,"Test sample tweet text.", lp);
    }

    @RequestMapping("/answer")
    @ResponseBody
    public AnswerResult answer(@RequestBody Answer answer, @ModelAttribute GameStatus status, Model model) {
        if(answer.getChoice() == 2) {
            status.setCorrectAnswers(status.getCorrectAnswers() + 1);
            status.setScore(status.getScore() + 10);
        }
        return new AnswerResult(answer.getChoice(), 2);
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
