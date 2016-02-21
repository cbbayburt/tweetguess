package com.dedeler.tweetguess.repository;

import com.dedeler.tweetguess.model.Category;
import com.dedeler.tweetguess.model.Person;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Aykut on 20.02.2016.
 */
@Repository
public interface PersonRepository extends CrudRepository<Person, Long> {

    List<Person> findTop3ByCategory(Category category);

}
