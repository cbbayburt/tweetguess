package com.dedeler.tweetguess.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

/**
 * Created by Aykut on 20.02.2016.
 */
@Data
@Entity
public class Person {

    @Id
    private Integer id;

    private String name;
    private Integer rank;
    private Integer tweetCnt;
    private Integer followerCnt;
    private String screenName;

    @OneToOne
    @JoinColumn(name = "categoryId")
    private Category category;
    private String lang;
    private Integer fetchedTweetCnt;

}
