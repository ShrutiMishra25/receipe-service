package com.example.recipe.service;

import com.example.recipe.model.RecipeDto;

import java.util.List;

/**
 * Interface for recipe service
 */
public interface RecipeService {
    /**
     * Method to get all recipe
     *
     * @return List<RecipeDto> List of the all the recipe present in database
     */
    public List<RecipeDto> getAllRecipes();

    /**
     * Method to get recipe based on selected condition
     *
     * @param recipe              The recipe object containg filter condition
     * @param ingredientCondition condition saying if the ingredient mentioned need to be included or excluder
     * @return List<Recipe> List of the all the recipe with given filter
     */
    List<RecipeDto> getRecipeByCondition(RecipeDto recipe, String ingredientCondition);

    /**
     * Method to add recipe
     *
     * @param recipe the recipe to be added
     * @return Recipe added recipe
     */
    public RecipeDto addRecipe(RecipeDto recipe);

    /**
     * Method to update recipe
     *
     * @param name   name of recipe to be updated
     * @param recipe the recipe to be added
     * @return Recipe updated recipe
     */
    public RecipeDto updateRecipe(String name, RecipeDto recipe);

    /**
     * @param name of recipe to be deleted
     * @return String stating deletion successful
     */
    public String deleteReceipe(String name);
}
