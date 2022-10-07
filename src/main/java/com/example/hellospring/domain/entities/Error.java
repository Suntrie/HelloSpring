package com.example.hellospring.domain.entities;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Data
@Table(name = "error")
public class Error {
    @Id
    private Long id;
    private Long deviceId;
    private Long startTime;
    private Long endTime;
    private String deviceType;
    private String errorType;
}
