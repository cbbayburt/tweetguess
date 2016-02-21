package com.dedeler.tweetguess.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/**
 * @author Can Bulut Bayburt
 */

@Data
@AllArgsConstructor
public class Question {
    private Integer index;
    private String tweet;
    private List<Person> people;
}
