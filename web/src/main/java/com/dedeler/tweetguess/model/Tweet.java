package com.dedeler.tweetguess.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Aykut on 20.02.2016.
 */
@Data
@Entity
@JsonIgnoreProperties({"createdAt","favoriteCount", "retweetCount", "person", "valid"})
public class Tweet {

    @Id
    private Long id;

    private Date createdAt;

    @Column(length = 4000)
    private String text;
    private Integer favoriteCount;
    private Long retweetCount;

    @ManyToOne
    @JoinColumn(name = "personId")
    private Person person;

    private Boolean valid;

}
