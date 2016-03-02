package com.dedeler.tweetguess.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Created by Aykut on 20.02.2016.
 */
@Data
@Entity
public class User {

    @Id
    @Column(length = 80)
    private String username;

}
