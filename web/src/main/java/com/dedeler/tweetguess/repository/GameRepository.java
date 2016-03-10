package com.dedeler.tweetguess.repository;

import com.dedeler.tweetguess.model.Game;
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

    @Query("select g.user, g.correctAnswers, g.score from Game g where g.score = (select max(score) from Game gg where gg.user = g.user AND gg.firstDayOfWeek = ?1) order by g.score desc, g.correctAnswers desc")
    List<Object[]> getWeeklyScoreList(LocalDate firstDayOfWeek);

}
