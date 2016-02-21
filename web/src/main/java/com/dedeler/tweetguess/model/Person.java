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
    private Long id;
    private String name;
    private String screenName;
    private String profileImageURL;
    private String url;
    private Boolean isProtected;
    private Integer followersCount;
    private Integer friendsCount;
    private Integer favouritesCount;

    @OneToOne
    @JoinColumn(name = "languageId")
    private Language language;

    @OneToOne
    @JoinColumn(name = "categoryId")
    private Category category;

}
