package com.example.hellospring.controllers;

import com.example.hellospring.services.ErrorService;
import com.example.hellospring.services.RecipeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/hibernate/load")
@RequiredArgsConstructor
public class LoadTestHibernateController {

    private final RecipeService recipeService;

    /*Оконная функция на основе спецификаций Java*/
    @GetMapping("/java-spec/aggregates")
    @ResponseStatus(HttpStatus.OK)
    public String getAggregatesByJavaSpec() {
        return recipeService.getAggregatesByJavaSpec();
    }

    /*Оконная функция на основе http clickhouse*/
    @GetMapping("/http-clickhouse/aggregates")
    @ResponseStatus(HttpStatus.OK)
    public String getAggregatesByHttpClickhouse() {
        return recipeService.getAggregatesByHttpClickhouse();
    }

    /*Оконная функция на основе jdbc*/
    @GetMapping("/jdbc/aggregates")
    @ResponseStatus(HttpStatus.OK)
    public String getAggregatesByJDBC() {
        return recipeService.getAggregatesByJDBC();
    }
}
