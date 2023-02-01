package com.example.recipe.model;


import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class RecipeDto {
    private String name;

    private String instructions;

    private Integer servings;

    private String type;

    private List<String> ingredients = new ArrayList<>();
}
