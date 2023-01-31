/*
package com.example.recipe.integration;

import com.example.recipe.controller.RecipeController;
import com.example.recipe.model.RecipeDto;
import com.example.recipe.service.RecipeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashSet;
import java.util.Set;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class RecipeApplicationIntegrationTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private RecipeController recipeController;

    @Autowired
    private RecipeService recipeService;

    @Test
    void testGetAllRecipes() throws Exception {
        RecipeDto recipe = new RecipeDto();
        Set<String> ingredient = new HashSet<>();
        ingredient.add("pasta");
        ingredient.add("tomato");
        recipe.setIngredients(ingredient);
        recipe.setInstructions("boil pasta and add pasta sauce");
        recipe.setName("pasta");
        recipe.setServings(2);
        recipe.setType("non-vegeterian");
        mockMvc.perform(get("/recipes"))
                .andExpect(status().isOk());
    }
}
*/
