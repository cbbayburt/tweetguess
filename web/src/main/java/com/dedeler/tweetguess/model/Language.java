package com.dedeler.tweetguess.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Created by Aykut on 20.02.2016.
 */
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Language {

    @Id
    @Column(length = 80)
    private String code;
    private String name;
    private String status;

}
