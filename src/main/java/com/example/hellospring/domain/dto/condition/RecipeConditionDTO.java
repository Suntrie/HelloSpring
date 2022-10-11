package com.example.hellospring.domain.dto.condition;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RecipeConditionDTO {
    private Long minCount;
    private String nameLikePattern;
}
