package com.dedeler.tweetguess.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.util.Date;

/**
 * Created by Aykut on 20.02.2016.
 */
@Data
@Entity
@JsonIgnoreProperties({"createdAt","favoriteCount", "retweetCount", "category", "language", "person"})
public class Tweet {

    @Id
    private Long id;

    private Date createdAt;
    private String text;
    private Integer favoriteCount;
    private Long retweetCount;

    @ManyToOne
    @JoinColumn(name = "categoryId")
    private Category category;

    @ManyToOne
    @JoinColumn(name = "languageId")
    private Language language;

    @ManyToOne
    @JoinColumn(name = "personId")
    private Person person;

    private Boolean valid;

}
