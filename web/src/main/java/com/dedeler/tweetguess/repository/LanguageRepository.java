package com.dedeler.tweetguess.repository;

import com.dedeler.tweetguess.model.Language;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by Aykut on 20.02.2016.
 */
public interface LanguageRepository extends CrudRepository<Language, String> {
}
