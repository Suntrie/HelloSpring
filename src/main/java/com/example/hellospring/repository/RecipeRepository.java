package com.example.hellospring.repository;

import com.example.hellospring.domain.entities.Recipe;
import com.example.hellospring.repository.aggregation.RecipeAggregatorRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface RecipeRepository extends JpaRepository<Recipe, String>, JpaSpecificationExecutor<Recipe>,
        RecipeAggregatorRepository {
    List<Recipe> findAll();
    List<Recipe> findByTitle(String title);

}
