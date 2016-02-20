package com.dedeler.tweetguess.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Aykut on 20.02.2016.
 */
@Controller
public class IndexController {

    @RequestMapping("/")
    public String index(){
        return "index";
    }

    @RequestMapping("/game")
    public void game() {}

    @RequestMapping("/loading")
    public void loading() {}

    @RequestMapping("/question")
    public void question() {}

    @RequestMapping("/leaderboard")
    public void leaderboard() {}
}
