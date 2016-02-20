package com.dedeler.tweetguess;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.annotation.PropertySource;

/**
 * Created by Aykut on 20.02.2016.
 */
@SpringBootApplication
@PropertySource("META-INF/MANIFEST.MF")
public class App extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

}