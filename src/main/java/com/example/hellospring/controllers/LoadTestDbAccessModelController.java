package com.example.hellospring.controllers;

import com.example.hellospring.services.RecipeService;
import com.example.hellospring.services.VetService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;

@RestController
@RequestMapping("/api/hibernate/load")
@RequiredArgsConstructor
@Slf4j
public class LoadTestDbAccessModelController {

    private final RecipeService recipeService;

    /*Оконная функция на основе спецификаций Java*/
    @GetMapping("/java-spec/aggregates")
    @ResponseStatus(HttpStatus.OK)
    public Long getAggregatesByJavaSpec() {
        return recipeService.getAggregatesByJavaSpec();
    }

    /*Оконная функция на основе http clickhouse*/
    @GetMapping("/http-clickhouse/aggregates")
    @ResponseStatus(HttpStatus.OK)
    public Long getAggregatesByHttpClickhouse() {
        return recipeService.getAggregatesByHttpClickhouse();
    }

    /*Оконная функция на основе jdbc*/
    @GetMapping("/jdbc/aggregates")
    @ResponseStatus(HttpStatus.OK)
    public Long getAggregatesByJDBC() throws SQLException {
        return recipeService.getAggregatesByJDBC();
    }

    /*Оконная функция на основе jdbc*/
    @GetMapping("/aggregates/test")
    @ResponseStatus(HttpStatus.OK)
    public String testAggregates() throws Exception {

        var iterationsNum = 100;

        List<Pair<Callable<Long>, String>> functions = Arrays.asList(
                //Pair.of(this::getAggregatesByJDBC, "JDBC"),
                //Pair.of(this::getAggregatesByHttpClickhouse, "Http"),
                Pair.of(this::getAggregatesByJavaSpec,"Spec")
        );

        for (var function : functions) {

            Long timeSum = 0L;

            for (int i = 0; i < iterationsNum; i++) {
                timeSum += function.getLeft().call();
            }

            log.info("-------------------------------------------------------------------------");
            log.info("Avg time for function {}: {}", function.getRight(), timeSum/iterationsNum);
            log.info("-------------------------------------------------------------------------");
        }

        return "test done";
    }

    @GetMapping("/java-spec/title-multiple-pk")
    @ResponseStatus(HttpStatus.OK)
    public String getByTitle() {
        return recipeService.findByTitle();
    }
}
