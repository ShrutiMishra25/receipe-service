package com.example.recipe.controller;

import com.example.recipe.model.RecipeDto;
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
    public ResponseEntity<List<RecipeDto>> getAllRecipes() {
        List<RecipeDto> recipe = recipeService.getAllRecipes();
        return new ResponseEntity<>(recipe, HttpStatus.OK);
    }

    /***
     * Method to add the recipe
     * @param recipe The recipe to be added
     * @return ResponseEntity<> response entity containing added recipe
     */
    @PostMapping("/recipes")
    public ResponseEntity<RecipeDto> addRecipe(@RequestBody RecipeDto recipe) {
        RecipeDto addedRecipe = recipeService.addRecipe(recipe);
        return new ResponseEntity<>(addedRecipe, HttpStatus.CREATED);
    }

    /***
     * Method to update the recipe
     * @param name name of the recipe to be updated
     * @param recipe recipe object to be updated
     * @return ResponseEntity<> response entity containing added recipe
     */
    @PutMapping("/recipes/{name}")
    public ResponseEntity<RecipeDto> updateRecipe(@PathVariable(name = "name") String name,
                                                  @RequestBody RecipeDto recipe) {
        RecipeDto updatedRecipe = recipeService.updateRecipe(name, recipe);
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
     * @param recipeDto           Recipe object containg the filter condition
     * @param ingredientCondition condition to include or exclude given recipe
     * @return
     */
    @GetMapping("/selected")
    public ResponseEntity<List<RecipeDto>> getRecipeByCondition(@RequestBody RecipeDto recipeDto, @RequestParam(name = "ingredientCondition", required = false, defaultValue = "include") String ingredientCondition) {
        List<RecipeDto> recipe = recipeService.getRecipeByCondition(recipeDto, ingredientCondition);
        return new ResponseEntity<>(recipe, HttpStatus.OK);
    }

}
