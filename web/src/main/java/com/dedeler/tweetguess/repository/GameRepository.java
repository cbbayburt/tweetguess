package com.dedeler.tweetguess.repository;

import com.dedeler.tweetguess.model.Game;
import com.dedeler.tweetguess.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

/**
 * Created by Aykut on 21.02.2016.
 */
@Repository
public interface GameRepository extends CrudRepository<Game, Integer> {

    @Query("select g.user, sum(g.correctAnswers) as correctAnswers, sum(g.score) as score from Game g where g.firstDayOfWeek = ?1 group by g.user order by score desc, correctAnswers desc")
    List<Object[]> getWeeklyScoreList(LocalDate firstDayOfWeek);

}
