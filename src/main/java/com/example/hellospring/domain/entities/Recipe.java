package com.example.hellospring.domain.entities;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/*
CREATE TABLE recipes
        (
        title String,
        ingredients Array(String),
        directions Array(String),
        link String,
        source LowCardinality(String),
        NER Array(String)
        ) ENGINE = MergeTree ORDER BY title;*/

@Entity
@Data
@Table(name = "recipes")
public class Recipe {

    @Id
    private String title;
    private String ingredients;
    private String directions;
    private String link;
    private String source;
    private String NER;

}
