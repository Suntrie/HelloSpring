package com.example.hellospring.controllers;

import com.example.hellospring.services.ErrorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/hibernate")
@RequiredArgsConstructor
public class HibernateController {

    private final ErrorService errorService;

    @GetMapping("/selected-errors-jpa")
    @ResponseStatus(HttpStatus.OK)
    public String getErrorsBySpring() {
        return errorService.selectSomeErrorsByJPA();
    }

    @GetMapping("/error-aggregates-nq")
    @ResponseStatus(HttpStatus.OK)
    public String getAggregatesByNativeQuery() {
        return errorService.getAggregatesByNativeQuery();
    }

    @GetMapping("/selected-errors-nq")
    @ResponseStatus(HttpStatus.OK)
    public String getErrorsByClickHouseNQ() {
        return errorService.selectSomeErrorsByClickHouseNQ();
    }

}
