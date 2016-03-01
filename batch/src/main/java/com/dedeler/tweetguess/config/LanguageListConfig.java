package com.dedeler.tweetguess.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Aykut on 1.03.2016.
 */
@Configuration
@ConfigurationProperties(prefix = "tweetguess")
public class LanguageListConfig {

    private List<String> languageList;

    LanguageListConfig() {
        this.languageList = new ArrayList<>();
    }

    public List<String> getLanguageList() {
        return this.languageList;
    }

}
