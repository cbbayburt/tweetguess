package com.dedeler.tweetguess.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

/**
 * @author Can Bulut Bayburt
 */

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @OneToOne
    @JoinColumn(name = "tweetId")
    private Tweet tweet;

    @Transient
    private Integer index;

    @Transient
    private List<Person> people;

    @Transient
    private Long startTime;
}
