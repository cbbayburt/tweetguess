package com.dedeler.tweetguess.config;

import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import javax.servlet.http.HttpServletRequest;
import java.util.Locale;

/**
 * @author Can Bulut Bayburt
 */

public class HeaderFirstSessionLocaleResolver extends SessionLocaleResolver {

    private final LocaleCollection availableLocales;

    public HeaderFirstSessionLocaleResolver(LocaleCollection availableLocales) {
        this.availableLocales = availableLocales;
    }

    /**
     * Tries to determine default locale from the request headers first.
     * If not found, sets the predetermined default locale.
     * @param request Http request
     * @return Default locale
     */
    @Override
    protected Locale determineDefaultLocale(HttpServletRequest request) {
        Locale defaultLocale = request.getLocale();
        if(defaultLocale == null || !availableLocales.contains(defaultLocale)) {
            defaultLocale = this.getDefaultLocale();
        }

        return defaultLocale;
    }
}
