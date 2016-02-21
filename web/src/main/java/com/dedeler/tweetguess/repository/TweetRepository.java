package com.dedeler.tweetguess.repository;

import com.dedeler.tweetguess.model.Category;
import com.dedeler.tweetguess.model.Tweet;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Aykut on 20.02.2016.
 */
@Repository
public interface TweetRepository extends CrudRepository<Tweet, Long> {

    List<Tweet> findFirst10ByCategory(Category category);

}
