package com.example.recipe.service;

import com.example.recipe.entity.Ingredient;
import com.example.recipe.entity.Recipe;

import java.util.List;

/**
 * Interface for recipe service
 */
public interface RecipeService {
    /**
     * Method to get all recipe
     *
     * @return List<Recipe> List of the all the recipe present in database
     */
    public List<Recipe> getAllRecipes();

    /**
     * Method to get recipe based on selected condition
     *
     * @param Recipe           The recipe object containg filter condition
     * @param ingredientCondition condition saying if the ingredient mentioned need to be included or excluded
     * @return List<Recipe> List of the all the recipe with given filter
     */
    List<Recipe> getRecipeByCondition(Recipe Recipe, String ingredientCondition);

    /**
     * Method to add recipe
     *
     * @param recipe the recipe to be added
     * @return Recipe added recipe
     */
    public Recipe addRecipe(Recipe recipe);

    /**
     * Method to update recipe
     *
     * @param name   name of recipe to be updated
     * @param recipe the recipe to be added
     * @return Recipe updated recipe
     */
    public Recipe updateRecipe(String name, Recipe recipe);

    /**
     *
     * @param name of recipe to be deleted
     * @return String stating deletion successful
     */
    public String deleteReceipe(String name);
}
