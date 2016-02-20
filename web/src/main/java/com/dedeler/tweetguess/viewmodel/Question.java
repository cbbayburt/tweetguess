package com.dedeler.tweetguess.viewmodel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author Can Bulut Bayburt
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Question {
    private Integer id;
    private Integer index;
    private String tweet;
    private List<People> people;
}
