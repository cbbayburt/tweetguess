package com.dedeler.tweetguess.model.view;

import com.dedeler.tweetguess.model.Category;
import com.dedeler.tweetguess.model.Language;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Can Bulut Bayburt
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GamePreferences {
    private Category category;
    private Language lang;
}
