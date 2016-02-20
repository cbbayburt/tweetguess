package com.dedeler.tweetguess.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.LocalDate;
import java.util.Date;

/**
 * Created by Aykut on 20.02.2016.
 */
@Data
@Entity
public class Tweet {

    @Id
    private Long id;
    private Date createdAt;
    private String text;
    private Integer favoriteCount;
    private Long retweetCount;
    private String langId;
    private String categoryId;
    private Long personId;

}
