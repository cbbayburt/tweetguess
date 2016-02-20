package com.dedeler.tweetguess.controller;

import com.dedeler.tweetguess.viewmodel.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Aykut on 20.02.2016.
 */
@SessionAttributes(types = {UserInfo.class, GamePreferences.class, GameStatus.class})
@Controller
public class IndexController {

    @ModelAttribute
    GameStatus gameStatus() {
        return new GameStatus(-1);
    }

    @RequestMapping("/")
    public String index(){
        return "index";
    }

    @RequestMapping(value = "/initgame", method = RequestMethod.POST)
    @ResponseBody
    public UserInfo initGame(@RequestBody UserInfo user, Model model, HttpServletRequest request) {
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
        List<People> lp = new ArrayList<>();
        lp.add(new People(1, "Aasd Zxc", "@aasdzxc", null));
        lp.add(new People(2, "Vbb Zxx", "@vbbzxx", null));
        lp.add(new People(3, "Qqwe Ads", "@qqweads", null));
        lp.add(new People(4, "Hjk Jk", "@hjkjk", null));

        status.setCurrentQuestion(status.getCurrentQuestion() + 1);
        return new Question(1, status.getCurrentQuestion(), "Test sample tweet text.", lp);
    }

    @RequestMapping("/answer")
    @ResponseBody
    public AnswerResult answer(@RequestBody Answer answer, Model model) {
        return new AnswerResult(answer.getChoice(), 2);
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

    @RequestMapping("/leaderboard")
    public void leaderboard() {}
}
