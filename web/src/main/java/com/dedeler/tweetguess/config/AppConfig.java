package com.dedeler.tweetguess.config;

import org.springframework.context.NoSuchMessageException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.AbstractLocaleResolver;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * @author Can Bulut Bayburt
 */

@Configuration
public class AppConfig extends WebMvcConfigurerAdapter {

    @Bean
    public LocaleResolver localeResolver() {
        AbstractLocaleResolver r = new HeaderFirstSessionLocaleResolver(getAvailableLocales());
        r.setDefaultLocale(Locale.US);
        return r;
    }

    @Bean
    public LocaleCollection getAvailableLocales() {
        Map<String, Locale> availableLocales = new HashMap<>();
        for (Locale locale : Locale.getAvailableLocales()) {
            try {
                String msg = messageSource().getMessage("locale", null, locale);
                if (!"default".equals(msg))
                    availableLocales.put(locale.toString(), locale);
            } catch (NoSuchMessageException ignored) {
            }
        }

        return new LocaleCollection(availableLocales.values());
    }

    @Bean
    public ReloadableResourceBundleMessageSource messageSource() {
        ReloadableResourceBundleMessageSource ms = new SerializableResourceBundleMessageSource();
        ms.setBasename("classpath:/i18n/messages");
        ms.setDefaultEncoding("UTF-8");
        ms.setFallbackToSystemLocale(false);
        return ms;
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
