package com.example.recipe.controller;

import com.example.recipe.entity.Recipe;
import com.example.recipe.service.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller class for recipe endpoint
 */
@RestController
public class RecipeController {
    @Autowired
    private RecipeService recipeService;

    /**
     * Method to get all recipes
     *
     * @return ResponseEntity<> response entity containing list of all the recipes
     */
    @GetMapping("/recipes")
    public ResponseEntity<List<Recipe>> getAllRecipes() {
        List<Recipe> recipe = recipeService.getAllRecipes();
        return new ResponseEntity<>(recipe, HttpStatus.OK);
    }

    /***
     * Method to add the recipe
     * @param recipe The recipe to be added
     * @return ResponseEntity<> response entity containing added recipe
     */
    @PostMapping("/recipes")
    public ResponseEntity<Recipe> addRecipe(@RequestBody Recipe recipe) {
        Recipe addedRecipe = recipeService.addRecipe(recipe);
        return new ResponseEntity<>(addedRecipe, HttpStatus.CREATED);
    }

    /***
     * Method to update the recipe
     * @param name name of the recipe to be updated
     * @param recipe recipe object to be updated
     * @return ResponseEntity<> response entity containing added recipe
     */
    @PutMapping("/recipes/{name}")
    public ResponseEntity<Recipe> updateRecipe(@PathVariable(name = "name") String name,
                                               @RequestBody Recipe recipe) {
        Recipe updatedRecipe = recipeService.updateRecipe(name, recipe);
        return new ResponseEntity<>(updatedRecipe, HttpStatus.CREATED);
    }

    /**
     * Method to delete the recipe
     *
     * @param name of the recipe to be deleted
     * @return String saying the recipe is deleted
     */
    @DeleteMapping("/recipes/{name}")
    public ResponseEntity<String> deleteReceipe(@PathVariable(name = "name") String name) {
        String message = recipeService.deleteReceipe(name);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    /**
     * Method to get the recipe based on provided filter
     *
     * @param Recipe              Recipe object containg the filter condition
     * @param ingredientCondition condition to include or exclude given recipe
     * @return
     */
    @GetMapping("/selected")
    public ResponseEntity<List<Recipe>> getRecipeByCondition(@RequestBody Recipe Recipe, @RequestParam(name = "ingredientCondition", required = false, defaultValue = "include") String ingredientCondition) {
        List<Recipe> recipe = recipeService.getRecipeByCondition(Recipe, ingredientCondition);
        return new ResponseEntity<>(recipe, HttpStatus.OK);
    }

}
