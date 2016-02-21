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

    Integer countByCategory(Category category);

    @Query(value = "select * from (select rownum as row_num, t.* from Tweet t left outer join (select q.tweet_id, q.game_id from Question q\n" +
            "inner join Game g on q.game_id = g.id where g.user_id=?2) q on t.id = q.tweet_id where t.category_id = ?1 \n" +
            "order by q.game_id asc, t.created_at desc) where row_num in ?3", nativeQuery = true)
    List<Tweet> getGeoRandomizedTweetsByCategory(Integer categoryId, String userId, List<Integer> randomNumberList);

}
