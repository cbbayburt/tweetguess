package com.dedeler.tweetguess.repository;

import com.dedeler.tweetguess.model.Category;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Aykut on 20.02.2016.
 */
@Repository
public interface CategoryRepository extends CrudRepository<Category, String> {
}
