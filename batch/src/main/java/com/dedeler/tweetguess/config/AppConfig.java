package com.dedeler.tweetguess.config;

import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

/**
 * @author Can Bulut Bayburt
 */

@Configuration
@EntityScan(basePackages = {"com.dedeler.tweetguess.model"}, basePackageClasses = {Jsr310JpaConverters.class})
public class AppConfig {
}
