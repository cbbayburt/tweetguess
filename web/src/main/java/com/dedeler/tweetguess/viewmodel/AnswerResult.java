package com.dedeler.tweetguess.viewmodel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Can Bulut Bayburt
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AnswerResult {
    private Integer userChoice;
    private Integer correctChoice;
}
