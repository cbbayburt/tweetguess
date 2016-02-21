package com.dedeler.tweetguess.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

/**
 * Created by Aykut on 20.02.2016.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Game {

    private List<Question> questionList;
    private Map<Integer, Integer> answerMap;
    private Question currentQuestion;

}
