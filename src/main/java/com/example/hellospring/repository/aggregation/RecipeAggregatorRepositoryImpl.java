package com.example.hellospring.repository.aggregation;

import com.example.hellospring.domain.entities.Recipe;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.Tuple;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.SingularAttribute;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toMap;

@RequiredArgsConstructor
@Component
public class RecipeAggregatorRepositoryImpl implements RecipeAggregatorRepository {
    private final EntityManager entityManager;

    @Override
    public Map<String, Long> groupAndCountHaving(SingularAttribute<Recipe, String> singularAttribute, Specification<Recipe> where) //TODO: что за типы здесь нужны?
    {
        final CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        final CriteriaQuery<Tuple> query = criteriaBuilder.createQuery(Tuple.class);
        final Class entityClass = Recipe.class;
        final Root<Recipe> root = query.from(entityClass);
        final Path<?> expression = root.get(singularAttribute);
        query.multiselect(expression, criteriaBuilder.count(root));
        
        //query.select(criteriaBuilder.tuple(expression, criteriaBuilder.count(root)));
        //query.where(where.toPredicate(root, query, criteriaBuilder));
        query.groupBy(expression);

        long startMillis = System.currentTimeMillis();
        final List<Tuple> resultList = entityManager.createQuery(query).getResultList();
        long endMillis = System.currentTimeMillis();

        System.out.println(endMillis - startMillis);
        return resultList.stream().
                collect(toMap(
                        t -> t.get(0, String.class),
                        t -> t.get(1, Long.class))
                );
    }
}
