package com.dedeler.tweetguess.config;

import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import twitter4j.TweetGuessTwitter;
import twitter4j.TweetGuessTwitterImpl;
import twitter4j.auth.AuthorizationFactory;
import twitter4j.conf.ConfigurationBuilder;

/**
 * Created by Aykut on 20.02.2016.
 */
@Configuration
public class TweetGuessConfig {

    @Value("${twitter.consumer-key}")
    private String consumerKey;

    @Value("${twitter.consumer-secret}")
    private String consumerSecret;

    @Value("${twitter.access-token}")
    private String accessToken;

    @Value("${twitter.access-token-secret}")
    private String accessTokenSecret;

    @Bean
    public TweetGuessTwitter tweetGuessTwitter() {
        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true)
                .setOAuthConsumerKey(consumerKey)
                .setOAuthConsumerSecret(consumerSecret)
                .setOAuthAccessToken(accessToken)
                .setOAuthAccessTokenSecret(accessTokenSecret);
        twitter4j.conf.Configuration configuration = cb.build();
        return new TweetGuessTwitterImpl(configuration, AuthorizationFactory.getInstance(configuration));
    }

    @Bean
    public DozerBeanMapper dozerBeanMapper() {
        return new DozerBeanMapper();
    }

}
