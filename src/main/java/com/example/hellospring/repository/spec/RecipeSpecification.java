package com.example.hellospring.repository.spec;

import com.example.hellospring.domain.dto.condition.RecipeConditionDTO;
import com.example.hellospring.domain.entities.Recipe;
import lombok.experimental.UtilityClass;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;

@UtilityClass
public class RecipeSpecification {

    public Specification<Recipe> createSpecificationByParams(RecipeConditionDTO recipeConditionDTO) {
        return Specification.where(whereRecipeTitleLike(recipeConditionDTO.getNameLikePattern()));
    }

    private static Specification<Recipe> whereRecipeTitleLike(String nameLikePattern) {
        return nameLikePattern == null ? null : (root, query, builder) -> builder.like(root.get("title"), "%" + nameLikePattern + "%");
    }

/*    private Specification<Recipe> groupBy() {
        return  (root, query, builder) -> {
            Predicate predicate = builder.conjunction();
            final Path<Recipe> expression = root.get("id");
            query.groupBy(expression);
            query.select(builder.tuple(expression, builder.count(root)));
            return predicate;
        };
    };*/

}
