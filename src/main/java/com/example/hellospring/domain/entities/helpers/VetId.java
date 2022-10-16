package com.example.hellospring.domain.entities.helpers;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class VetId implements Serializable {
    private Long customerId;
    private Long animalPassportNumber;
}
