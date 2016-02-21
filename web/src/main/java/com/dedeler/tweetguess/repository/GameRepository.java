package com.dedeler.tweetguess.repository;

import com.dedeler.tweetguess.model.Game;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Aykut on 21.02.2016.
 */
@Repository
public interface GameRepository extends CrudRepository<Game, Integer> {
}