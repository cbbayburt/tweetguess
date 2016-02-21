package com.dedeler.tweetguess.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author Can Bulut Bayburt
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Leaderboard {
    List<Score> scores;
    Category category;
    Language language;
}
