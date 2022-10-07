package com.example.hellospring.services;

import com.example.hellospring.domain.entities.Error;
import com.example.hellospring.domain.specificaitons.ErrorSpecification;
import com.example.hellospring.repository.ErrorRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ErrorService {
    private final ErrorRepository errorRepository;

    public String selectSomeErrorsByJPA() {

        List<Error> errorList = errorRepository.findByDeviceTypeAndStartTimeBetween("Roboter X",
                1546300800L,
                1546387140L);

        return errorList.toString();
    }

    public String getAggregatesByNativeQuery() {
        List<ErrorSpecification> errorSpecifications = errorRepository.findByNativeQuery(1440L,
                1546300800L,
                1546387140L, "Roboter X");

        StringBuilder result = new StringBuilder();

        for (ErrorSpecification errorSpecification: errorSpecifications){
            result.append(String.format("%s %s <br>", errorSpecification.getErrorType(),
                    errorSpecification.getAvgVal()));
        }

        return result.toString();
    }

    public String selectSomeErrorsByClickHouseNQ() {
        List<Error> ties = errorRepository.findWithTies();
        return ties.toString();
    }
}
