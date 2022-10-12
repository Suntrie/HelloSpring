package com.example.hellospring.domain.entities;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

// Recipe table has 2kk+ of records
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
