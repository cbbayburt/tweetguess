package com.dedeler.tweetguess.repository;

import com.dedeler.tweetguess.model.Category;
import com.dedeler.tweetguess.model.Language;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Aykut on 20.02.2016.
 */
@Repository
public interface CategoryRepository extends CrudRepository<Category, String> {

    List<Category> findByLanguage(Language language);
    Category findBySlugAndNameAndSizeAndLanguage(String slug, String name, Integer size, Language language);

}
