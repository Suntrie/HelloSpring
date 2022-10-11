package com.example.hellospring.repository;

import com.example.hellospring.domain.entities.Recipe;/*
import com.example.hellospring.repository.aggregation.AggregatorRepository;*/
import com.example.hellospring.repository.aggregation.RecipeAggregatorRepository;
import com.example.hellospring.repository.spec.RecipeSpecification;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;

public interface RecipeRepository extends JpaRepository<Recipe, String>, JpaSpecificationExecutor<Recipe>,
        RecipeAggregatorRepository {
    List<Recipe> findAll();
    

   /* @Override
    default Class<Recipe> getEntityClass(){
        return Recipe.class;
    }*/

    //select title, count(*) as count from recipes group by title having count >=7;
}
