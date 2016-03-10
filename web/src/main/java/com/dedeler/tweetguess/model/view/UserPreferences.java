package com.dedeler.tweetguess.model.view;

import com.dedeler.tweetguess.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Can Bulut Bayburt
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserPreferences {
    User user;
    GamePreferences preferences;
}
