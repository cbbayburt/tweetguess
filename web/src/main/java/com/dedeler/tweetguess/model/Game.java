package com.dedeler.tweetguess.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * Created by Aykut on 20.02.2016.
 */
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private LocalDateTime startTime;
    private LocalDateTime endTime;

    @OneToOne
    @JoinColumn(name = "userId")
    private User user;

    @OneToOne
    @JoinColumn(name = "categoryId")
    private Category category;

    @OneToOne
    @JoinColumn(name = "languageId")
    private Language language;

    @OneToMany
    @JoinColumn(name = "gameId")
    private List<Question> questionList;

    @Transient
    private Map<Integer, Integer> answerMap;

    @Transient
    private Question currentQuestion;
    private Integer correctAnswers;
    private Integer score;
}
