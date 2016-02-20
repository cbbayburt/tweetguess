package com.dedeler.tweetguess.model;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;

/**
 * Created by Aykut on 20.02.2016.
 */
@Data
@Entity
public class Game {

    @Id
    private Integer id;

    private LocalDate startTime;
    private LocalDate endTime;

    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;

    @OneToOne
    @JoinColumn(name = "categoryId")
    private Category category;
    private String lang;

}
