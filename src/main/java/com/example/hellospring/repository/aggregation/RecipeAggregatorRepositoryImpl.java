package com.example.hellospring.repository.aggregation;

import com.example.hellospring.domain.entities.Recipe;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.Tuple;
import javax.persistence.criteria.*;
import javax.persistence.metamodel.SingularAttribute;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toMap;

@Slf4j
@RequiredArgsConstructor
@Component
public class RecipeAggregatorRepositoryImpl implements RecipeAggregatorRepository {
    private final EntityManager entityManager;

    @Override
    public Pair<Map<String, Long>, Long> groupAndCountHaving(SingularAttribute<Recipe, String> singularAttribute, Specification<Recipe> where)
    {
        final CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        final CriteriaQuery<Tuple> query = criteriaBuilder.createQuery(Tuple.class);
        final Root<Recipe> root = query.from(Recipe.class);
        final Path<?> expression = root.get(singularAttribute);

        var expressionCount =criteriaBuilder.prod(criteriaBuilder.count(root), 1000);

        //select multiple fields & custom operation
        query.multiselect(expression, expressionCount);

        //add where clause from specification
        query.where(where.toPredicate(root, query, criteriaBuilder));

        //groupBy
        query.groupBy(expression);

        // having
        query.having(criteriaBuilder.gt(expressionCount, 1000));

        //custom ordering
        Expression<String> id = root.get(singularAttribute);
        Expression<?> sortExpression = criteriaBuilder.selectCase()
                .when(criteriaBuilder.like(id, "%e%"), 1)
                .otherwise(0);
        query.orderBy(criteriaBuilder.asc(sortExpression));

        long startMillis = System.currentTimeMillis();
        long duration;

        final List<Tuple> resultList = entityManager.createQuery(query).getResultList();
        long endMillis = System.currentTimeMillis();

        duration = endMillis - startMillis;

        log.info("Hibernate time: {}", duration);
        log.info("Hibernate size: {}", resultList.size());

        return Pair.of(resultList.stream().
                collect(toMap(
                        t -> t.get(0, String.class),
                        t -> t.get(1, Long.class))
                ), duration);
    }
}
