package com.example.hellospring.repository.aggregation;

import com.example.hellospring.domain.entities.Recipe;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.metamodel.SingularAttribute;
import java.util.Map;

public interface RecipeAggregatorRepository {

    Pair<Map<String, Long>, Long> groupAndCountHaving(SingularAttribute<Recipe, String> singularAttribute, Specification<Recipe> where); //TODO: что за типы здесь нужны?

}
