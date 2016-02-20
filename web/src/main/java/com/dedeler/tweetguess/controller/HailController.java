package com.dedeler.tweetguess.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Aykut on 20.02.2016.
 */
@RestController
@RequestMapping("/hail")
public class HailController {

    @RequestMapping(value = "/{name}")
    public String getHail(@PathVariable String name) {
        return "All hail " + name;
    }

}
