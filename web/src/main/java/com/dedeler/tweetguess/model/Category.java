package com.dedeler.tweetguess.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by Aykut on 20.02.2016.
 */
@Data
@Entity
@IdClass(Category.CategoryId.class)
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties({"language"})
public class Category {

    @Id
    @Column(length = 80)
    private String slug;

    @Id
    @Column(length = 80)
    private String languageId;

    @ManyToOne
    @JoinColumn(updatable = false, insertable = false, name = "languageId")
    private Language language;

    private String name;
    private Integer size;

    @Data
    static class CategoryId implements Serializable {
        private String slug;
        private String languageId;
    }

}
