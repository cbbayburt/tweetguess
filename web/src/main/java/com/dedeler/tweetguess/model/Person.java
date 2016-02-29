package com.dedeler.tweetguess.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

/**
 * Created by Aykut on 20.02.2016.
 */
@Data
@Entity
@JsonIgnoreProperties("category")
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

    @ManyToMany
    @JoinTable(name = "categoryPersonLk",
            joinColumns = @JoinColumn(name = "personId"),
            inverseJoinColumns = {
                    @JoinColumn(name = "languageId"),
                    @JoinColumn(name = "slug")
            })
    private List<Category> category;

}
