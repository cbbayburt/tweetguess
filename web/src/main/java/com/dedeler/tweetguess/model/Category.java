package com.dedeler.tweetguess.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * Created by Aykut on 20.02.2016.
 */
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties({"language"})
public class Category {

    @Id
    private Integer id;

    private String slug;
    private String name;
    private Integer size;

    @ManyToOne
    @JoinColumn(name = "languageId")
    private Language language;

}
