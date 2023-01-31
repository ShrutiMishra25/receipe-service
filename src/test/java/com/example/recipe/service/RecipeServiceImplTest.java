package com.example.recipe.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.recipe.entity.Recipe;
import com.example.recipe.repository.IngredientRepository;
import com.example.recipe.repository.ReceipeRepository;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {RecipeServiceImpl.class})
@ExtendWith(SpringExtension.class)
class RecipeServiceImplTest {
    @MockBean
    private IngredientRepository ingredientRepository;

    @MockBean
    private ReceipeRepository receipeRepository;

    @Autowired
    private RecipeServiceImpl recipeServiceImpl;

    /**
     * Method under test: {@link RecipeServiceImpl#getAllRecipes()}
     */
    @Test
    void testGetAllRecipes() {
        ArrayList<Recipe> recipeList = new ArrayList<>();
        when(receipeRepository.findAll()).thenReturn(recipeList);
        List<Recipe> actualAllRecipes = recipeServiceImpl.getAllRecipes();
        assertSame(recipeList, actualAllRecipes);
        assertTrue(actualAllRecipes.isEmpty());
        verify(receipeRepository).findAll();
    }

    /**
     * Method under test: {@link RecipeServiceImpl#getRecipeByCondition(Recipe, String)}
     */
    @Test
    void testGetRecipeByCondition() {
        ArrayList<Recipe> recipeList = new ArrayList<>();
        when(receipeRepository.findAll((Specification<Recipe>) any())).thenReturn(recipeList);

        Recipe recipe = new Recipe();
        recipe.setId(1);
        recipe.setIngredients(new ArrayList<>());
        recipe.setInstructions("Instructions");
        recipe.setName("Name");
        recipe.setServings(1);
        recipe.setType("Type");
        List<Recipe> actualRecipeByCondition = recipeServiceImpl.getRecipeByCondition(recipe, "Ingredient Condition");
        assertSame(recipeList, actualRecipeByCondition);
        assertTrue(actualRecipeByCondition.isEmpty());
        verify(receipeRepository).findAll((Specification<Recipe>) any());
    }

    /**
     * Method under test: {@link RecipeServiceImpl#addRecipe(Recipe)}
     */
    @Test
    void testAddRecipe() {
        Recipe recipe = new Recipe();
        recipe.setId(1);
        recipe.setIngredients(new ArrayList<>());
        recipe.setInstructions("Instructions");
        recipe.setName("Name");
        recipe.setServings(1);
        recipe.setType("Type");
        when(receipeRepository.save((Recipe) any())).thenReturn(recipe);

        Recipe recipe1 = new Recipe();
        recipe1.setId(1);
        recipe1.setIngredients(new ArrayList<>());
        recipe1.setInstructions("Instructions");
        recipe1.setName("Name");
        recipe1.setServings(1);
        recipe1.setType("Type");
        assertSame(recipe, recipeServiceImpl.addRecipe(recipe1));
        verify(receipeRepository).save((Recipe) any());
    }

    /**
     * Method under test: {@link RecipeServiceImpl#updateRecipe(String, Recipe)}
     */
    @Test
    void testUpdateRecipe() {
        Recipe recipe = new Recipe();
        recipe.setId(1);
        recipe.setIngredients(new ArrayList<>());
        recipe.setInstructions("Instructions");
        recipe.setName("Name");
        recipe.setServings(1);
        recipe.setType("Type");

        Recipe recipe1 = new Recipe();
        recipe1.setId(1);
        recipe1.setIngredients(new ArrayList<>());
        recipe1.setInstructions("Instructions");
        recipe1.setName("Name");
        recipe1.setServings(1);
        recipe1.setType("Type");
        when(receipeRepository.findByName((String) any())).thenReturn(recipe);
        when(receipeRepository.save((Recipe) any())).thenReturn(recipe1);

        Recipe recipe2 = new Recipe();
        recipe2.setId(1);
        recipe2.setIngredients(new ArrayList<>());
        recipe2.setInstructions("Instructions");
        recipe2.setName("Name");
        recipe2.setServings(1);
        recipe2.setType("Type");
        assertSame(recipe1, recipeServiceImpl.updateRecipe("Name", recipe2));
        verify(receipeRepository).findByName((String) any());
        verify(receipeRepository).save((Recipe) any());
        assertEquals(1, recipe2.getId().intValue());
    }

    /**
     * Method under test: {@link RecipeServiceImpl#deleteReceipe(String)}
     */
    @Test
    void testDeleteReceipe() {
        Recipe recipe = new Recipe();
        recipe.setId(1);
        recipe.setIngredients(new ArrayList<>());
        recipe.setInstructions("Instructions");
        recipe.setName("Name");
        recipe.setServings(1);
        recipe.setType("Type");
        doNothing().when(receipeRepository).deleteById((Integer) any());
        when(receipeRepository.findByName((String) any())).thenReturn(recipe);
        assertEquals("Recipe Name deleted successfully!", recipeServiceImpl.deleteReceipe("Name"));
        verify(receipeRepository).findByName((String) any());
        verify(receipeRepository).deleteById((Integer) any());
    }

    /**
     * Method under test: {@link RecipeServiceImpl#findByCriteria(Recipe, String)}
     */
    @Test
    void testFindByCriteria() {
        ArrayList<Recipe> recipeList = new ArrayList<>();
        when(receipeRepository.findAll((Specification<Recipe>) any())).thenReturn(recipeList);

        Recipe recipe = new Recipe();
        recipe.setId(1);
        recipe.setIngredients(new ArrayList<>());
        recipe.setInstructions("Instructions");
        recipe.setName("Name");
        recipe.setServings(1);
        recipe.setType("Type");
        List<Recipe> actualFindByCriteriaResult = recipeServiceImpl.findByCriteria(recipe, "Ingredient Condition");
        assertSame(recipeList, actualFindByCriteriaResult);
        assertTrue(actualFindByCriteriaResult.isEmpty());
        verify(receipeRepository).findAll((Specification<Recipe>) any());
    }
}

