package com.dedeler.tweetguess.config;

import org.springframework.util.StringUtils;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.support.RequestContextUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collection;
import java.util.Locale;

/**
 * @author Can Bulut Bayburt
 */

public class SupportedLocaleChangeInterceptor extends LocaleChangeInterceptor {

    private Collection<Locale> localeCollection;

    public SupportedLocaleChangeInterceptor(Collection<Locale> supportedLocales) {
        localeCollection = supportedLocales;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws ServletException {
        String newLocale = request.getParameter(this.getParamName());
        if(newLocale != null) {
            LocaleResolver localeResolver = RequestContextUtils.getLocaleResolver(request);
            if(localeResolver == null) {
                throw new IllegalStateException("No LocaleResolver found: not in a DispatcherServlet request?");
            }

            try {
                if(localeCollection.contains(StringUtils.parseLocaleString(newLocale)))
                    localeResolver.setLocale(request, response, StringUtils.parseLocaleString(newLocale));
                else
                    this.logger.debug("Locale is not supported: " + newLocale);
            } catch (IllegalArgumentException var7) {
                if(!this.isIgnoreInvalidLocale()) {
                    throw var7;
                }

                this.logger.debug("Ignoring invalid locale value [" + newLocale + "]: " + var7.getMessage());
            }
        }

        return true;
    }
}
