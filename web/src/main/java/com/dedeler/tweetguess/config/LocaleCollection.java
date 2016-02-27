package com.dedeler.tweetguess.config;

import java.util.Collection;
import java.util.HashSet;
import java.util.Locale;

/**
 * @author Can Bulut Bayburt
 */

public class LocaleCollection extends HashSet<Locale> {
    public LocaleCollection(Collection<? extends Locale> collection) {
        super(collection);
    }
}
