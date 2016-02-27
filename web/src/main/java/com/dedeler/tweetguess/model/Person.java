package com.dedeler.tweetguess.model;

import lombok.Data;

import javax.persistence.*;

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
    @JoinColumns({
            @JoinColumn(name = "languageId"),
            @JoinColumn(name = "slug")
    })
    private Category category;

}
