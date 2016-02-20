package com.dedeler.tweetguess.repository;

import com.dedeler.tweetguess.model.Person;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Aykut on 20.02.2016.
 */
@Repository
public interface PersonRepository extends CrudRepository<Person, Integer> {
}
