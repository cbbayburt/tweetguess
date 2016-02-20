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
public class People {
    private Integer id;
    private String name;
    private String screenName;
    private String profileUrl;
}
