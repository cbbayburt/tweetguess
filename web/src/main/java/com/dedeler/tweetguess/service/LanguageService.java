package com.dedeler.tweetguess.service;

import com.dedeler.tweetguess.model.Language;
import com.dedeler.tweetguess.repository.LanguageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Aykut on 21.02.2016.
 */
@Service
public class LanguageService {

    @Autowired
    private LanguageRepository languageRepository;

    public List<Language> getLanguagesOrderByName() {
        return languageRepository.findAllByOrderByNameAsc();
    }

}
