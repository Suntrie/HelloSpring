package com.example.hellospring.domain.entities;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Data
@Table(name = "katze")
public class Katze {
    @Id
    private Integer catId;
    private String catName;
}
