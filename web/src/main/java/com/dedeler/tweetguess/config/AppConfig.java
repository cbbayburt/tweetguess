package com.dedeler.tweetguess.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * @author Can Bulut Bayburt
 */

@Configuration
public class AppConfig extends WebMvcConfigurerAdapter {
    @Autowired
    MessageSource messageSource;

    @Bean
    public LocaleResolver localeResolver() {
        return new SessionLocaleResolver();
    }

    @Bean
    public LocaleCollection getAvailableLocales() {
        Map<String, Locale> availableLocales = new HashMap<>();
        for (Locale locale : Locale.getAvailableLocales()) {
            try {
                String msg = messageSource.getMessage("locale", null, locale);
                if (!"default".equals(msg))
                    availableLocales.put(locale.toString(), locale);
            } catch (NoSuchMessageException ignored) {
            }
        }

        return new LocaleCollection(availableLocales.values());
    }

    @Bean
    public SupportedLocaleChangeInterceptor localeChangeInterceptor() {
        SupportedLocaleChangeInterceptor i = new SupportedLocaleChangeInterceptor(getAvailableLocales());
        i.setIgnoreInvalidLocale(true);
        i.setParamName("lang");
        return i;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(localeChangeInterceptor());
    }
}
