package com.example.hellospring.controllers;

import com.example.hellospring.services.VetService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;

@RestController
@RequestMapping("/api/hibernate/load")
@RequiredArgsConstructor
@Slf4j
public class LoadTestDbInsertController{
    private final VetService vetService;

    @Transactional
    @PostMapping("/java-spec/insert-new-vets")
    @ResponseStatus(HttpStatus.OK)
    public Long generateNewByJavaSpec() throws Exception {
        return vetService.generate100kVetByJavaSpec();
    }

    @Transactional
    @PostMapping("/jdbc/insert-new-vets")
    @ResponseStatus(HttpStatus.OK)
    public Long generateNewByJDBC() throws Exception {
        return vetService.generate100kVetByJDBC();
    }

    @GetMapping("/java-spec/check-all-vets")
    @ResponseStatus(HttpStatus.OK)
    public String getAllVets() {
        return vetService.getAllVets();
    }

    @PostMapping("/insert-new-vets-test/test")
    @ResponseStatus(HttpStatus.OK)
    public String testInsertions() throws Exception {

        var iterationsNum = 10;


        List<Pair<Callable<Long>, String>> functions = Arrays.asList(
                //Pair.of(this::generateNewByJDBC, "JDBC")//,
                Pair.of(this::generateNewByJavaSpec,"Spec")
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
}
