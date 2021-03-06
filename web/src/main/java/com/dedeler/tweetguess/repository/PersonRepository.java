package com.dedeler.tweetguess.repository;

import com.dedeler.tweetguess.model.Category;
import com.dedeler.tweetguess.model.Person;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Aykut on 20.02.2016.
 */
@Repository
public interface PersonRepository extends CrudRepository<Person, Long> {

    List<Person> findByCategory(Category category);

    //Used in batch application
    @SuppressWarnings("unused")
    @Query("select p from Person p JOIN FETCH p.category where p.id = ?1")
    Person findOneFullLoad(Long id);

}
