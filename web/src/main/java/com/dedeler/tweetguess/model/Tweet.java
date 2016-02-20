package com.dedeler.tweetguess.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.LocalDate;

/**
 * Created by Aykut on 20.02.2016.
 */
@Data
@Entity
public class Tweet {

    @Id
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "personId")
    private Person person;

    @ManyToOne
    @JoinColumn(name = "categoryId")
    private Category category;

    private LocalDate createdTime;
    private Integer retweetCnt;
    private Integer favouriteCnt;
    private String lang;
    private String text;

}
