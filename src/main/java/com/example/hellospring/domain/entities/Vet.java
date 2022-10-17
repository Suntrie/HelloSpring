package com.example.hellospring.domain.entities;

import com.example.hellospring.domain.entities.helpers.VetId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;


@Entity
@Table(name = "vet")
@IdClass(VetId.class)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Vet implements Serializable {
    @Id
    private Long customerId;
    private String animalName;
    private String animalKind;

    @Id
    private Long animalPassportNumber;
    private Long timestamp;

    // It seems to be impossible to merge rows if we use synthetic key (rows are merged regarding 'order by' columns)
    // "In this case the primary key expression tuple must be a prefix of the sorting key expression tuple."
    //@Id
    //@GeneratedValue(strategy = GenerationType.SEQUENCE)
    //private Long recordId;

    public Vet(Vet a){
        this.customerId = a.getCustomerId();
        this.animalName = a.getAnimalName();
        this.animalPassportNumber = a.getAnimalPassportNumber();
        this.timestamp = a.getTimestamp();
        this.animalKind = a.getAnimalKind();
    }
}
