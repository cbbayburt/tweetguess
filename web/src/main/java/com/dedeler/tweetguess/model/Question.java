package com.dedeler.tweetguess.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import java.time.LocalDate;

/**
 * Created by Aykut on 20.02.2016.
 */
@Data
@Entity
public class Question {

    @Id
    private Integer id;

    @OneToOne
    @JoinColumn(name = "tweetId")
    private Tweet tweet;
    private String choices;
    private LocalDate askedTime;

}
