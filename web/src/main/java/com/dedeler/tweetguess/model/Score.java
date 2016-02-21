package com.dedeler.tweetguess.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Can Bulut Bayburt
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Score {
    private String username;
    private boolean user;
    private Integer rank;
    private Integer tweets;
    private Integer score;
}
