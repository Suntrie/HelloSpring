/*
package com.example.hellospring.repository.aggregation;

import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

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
@Repository
public abstract class AggregatorRepositoryBaseImpl<T, Z> implements AggregatorRepository<T, Z> {
    private final EntityManager entityManager;

    @Override
    public Map<Z, Long> customQuery(SingularAttribute<T, Z> singularAttribute*/
/*, Specification<T> where*//*
) {
        final CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        final CriteriaQuery<Tuple> query = criteriaBuilder.createQuery(Tuple.class);
        final Class<T> entityClass = getEntityClass();
        final Root<T> root = query.from(entityClass);
        final Path<?> expression = root.get(singularAttribute);
        query.multiselect(expression, criteriaBuilder.count(root));
        query.select(criteriaBuilder.tuple(expression, criteriaBuilder.count(root)));
        //query.where(where.toPredicate(root, query, criteriaBuilder));
        query.groupBy(expression);
        final List<Tuple> resultList = entityManager.createQuery(query).getResultList();
        return resultList.stream()
                .collect(toMap(
                        t -> t.get(0, singularAttribute.getJavaType()),
                        t -> t.get(1, Long.class))
                );
    }
}
*/
