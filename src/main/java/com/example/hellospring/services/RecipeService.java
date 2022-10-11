package com.example.hellospring.services;

import com.example.hellospring.domain.dto.condition.RecipeConditionDTO;
import com.example.hellospring.domain.entities.Recipe;
import com.example.hellospring.domain.entities.Recipe_;
import com.example.hellospring.repository.RecipeRepository;
import com.example.hellospring.repository.spec.RecipeSpecification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class RecipeService {

/*    public String getAggregatesByNativeQuery() {
        List<ErrorProjection> errorProjections = errorRepository.findByNativeQuery(1440L,
                1546300800L,
                1546387140L, "Roboter X");

        StringBuilder result = new StringBuilder();

        for (ErrorProjection errorProjection : errorProjections){
            result.append(String.format("%s %s <br>", errorProjection.getErrorType(),
                    errorProjection.getAvgVal()));
        }

        return result.toString();
    }*/

    private final RecipeRepository recipeRepository;

    public String getAggregatesByJDBC() {
        return "TODO";
    }

    public String getAggregatesByHttpClickhouse() {
        return "TODO";
    }

    public String getAggregatesByJavaSpec() {
        return recipeRepository.groupAndCountHaving(Recipe_.title, RecipeSpecification.createSpecificationByParams
                (RecipeConditionDTO.builder().build())).toString();
    }
}
