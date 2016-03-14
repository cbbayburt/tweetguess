package com.dedeler.tweetguess.repository;

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

    @Query(value = "select count(*) from Tweet t inner join t.person p inner join p.category c where c.slug = ?1 and c.languageId = ?2 and t.valid = ?3")
    Integer getTweetCountByCategoryAndValid(String slug, String language, Boolean valid);

    @Query(value = "select main.* from (SELECT @rowid\\:=0) as init, (select @rowid\\:=@rowid+1 as row_num, t.* from tweet t inner join person p on t.person_id = p.id " +
            "inner join category_person_lk cplk on p.id = cplk.person_id inner join category g on cplk.language_id = g.language_id and cplk.slug = g.slug " +
            "left outer join (select q.tweet_id, q.game_id from question q inner join game g on q.game_id = g.id where g.user_id = ?3) q on t.id = q.tweet_id " +
            "where g.slug = ?1 and g.language_id = ?2 and t.valid = ?5 order by q.game_id asc, t.created_at desc) as main where row_num in ?4", nativeQuery = true)
    List<Tweet> getGeoRandomizedTweetsByCategoryAndValid(String slug, String language, String userId, List<Integer> randomNumberList, Boolean valid);

}
