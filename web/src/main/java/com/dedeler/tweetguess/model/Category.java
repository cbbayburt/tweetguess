package com.dedeler.tweetguess.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Created by Aykut on 20.02.2016.
 */
@Data
@Entity
public class Category {

    @Id
    private Integer id;
    private String name;
    private String lang;

}
