package com.dedeler.tweetguess.repository;

import com.dedeler.tweetguess.model.Category;
import com.dedeler.tweetguess.model.Tweet;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Aykut on 20.02.2016.
 */
@Repository
public interface TweetRepository extends CrudRepository<Tweet, Long> {

    Integer countByCategoryAndValid(Category category, Boolean valid);

    @Query(value = "select * from (select rownum as row_num, t.* from Tweet t left outer join (select q.tweet_id, q.game_id from Question q\n" +
            "inner join Game g on q.game_id = g.id where g.user_id = ?3) q on t.id = q.tweet_id where t.slug = ?1 and t.language_id = ?2 and t.valid = ?5 \n" +
            "order by q.game_id asc, t.created_at desc) where row_num in ?4", nativeQuery = true)
    List<Tweet> getGeoRandomizedTweetsByCategoryAndValid(String slug, String language, String userId, List<Integer> randomNumberList, Boolean valid);

}
