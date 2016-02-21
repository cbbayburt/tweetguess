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
public class LangCategory {
    List<Category> categories;
    List<Language> languages;
}
