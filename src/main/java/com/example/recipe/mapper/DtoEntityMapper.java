package com.example.recipe.mapper;

import com.example.recipe.entity.Ingredient;
import com.example.recipe.entity.Recipe;
import com.example.recipe.model.RecipeDto;
import com.example.recipe.repository.IngredientRepository;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
@Component
public class DtoEntityMapper {

    @Resource
    IngredientRepository ingredientRepository;

    public RecipeDto mapEntitytoDto(
            Recipe recipe
    ) {
        RecipeDto recipeDto = new RecipeDto();
        recipeDto.setName(recipe.getName());
        recipeDto.setType(recipe.getType());
        recipeDto.setServings(recipe.getServings());
        recipeDto.setInstructions(recipe.getInstructions());
        recipeDto.setIngredients(recipe.getIngredients().stream().map(Ingredient::getName).collect(Collectors.toList()));
        return recipeDto;
    }

    public Recipe mapDtoToEntity(
            RecipeDto recipeDto) {
        Recipe recipe = new Recipe();
        recipe.setName(recipeDto.getName());
        recipe.setType(recipeDto.getType());
        recipe.setServings(recipeDto.getServings());
        recipe.setInstructions(recipeDto.getInstructions());
        if (null == recipe.getIngredients()) {
            recipe.setIngredients(new ArrayList<>());
        }
        List<Ingredient> ingredientList = new ArrayList<>();
        recipeDto.getIngredients().stream().forEach(recipeName -> {
            Ingredient ingredient = ingredientRepository.findByName(recipeName);
            ingredient.setName(recipeName);
            ingredientList.add(ingredient);
        });
        recipe.setIngredients(ingredientList);
        return recipe;
    }

}
