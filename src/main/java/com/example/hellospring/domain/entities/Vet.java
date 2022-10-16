package com.example.hellospring.domain.entities;

import com.example.hellospring.domain.entities.helpers.VetId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@Entity
@Table(name = "vet")
@IdClass(VetId.class)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Vet {
    @Id
    private Long customerId;
    private String animalName;
    private String animalKind;
    @Id
    private Long animalPassportNumber;
    private Long timestamp;
}
