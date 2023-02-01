package com.example.recipe.controller;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

import com.example.recipe.entity.Recipe;
import com.example.recipe.service.RecipeService;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ContextConfiguration(classes = {RecipeController.class})
@ExtendWith(SpringExtension.class)
class RecipeControllerTest {
    @Autowired
    private RecipeController recipeController;

    @MockBean
    private RecipeService recipeService;

    /**
     * Method under test: {@link RecipeController#getAllRecipes()}
     */
    @Test
    void testGetAllRecipes() throws Exception {
        when(recipeService.getAllRecipes()).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/recipes");
        MockMvcBuilders.standaloneSetup(recipeController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    /**
     * Method under test: {@link RecipeController#getAllRecipes()}
     */
    @Test
    void testGetAllRecipes2() throws Exception {
        when(recipeService.getAllRecipes()).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder getResult = MockMvcRequestBuilders.get("/recipes");
        getResult.characterEncoding("Encoding");
        MockMvcBuilders.standaloneSetup(recipeController)
                .build()
                .perform(getResult)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    /**
     * Method under test: {@link RecipeController#addRecipe(Recipe)}
     */
    @Test
    void testAddRecipe() throws Exception {
        Recipe recipe = new Recipe();
        recipe.setId(1);
        recipe.setIngredients(new ArrayList<>());
        recipe.setInstructions("Instructions");
        recipe.setName("Name");
        recipe.setServings(1);
        recipe.setType("Type");
        when(recipeService.addRecipe((Recipe) any())).thenReturn(recipe);

        Recipe recipe1 = new Recipe();
        recipe1.setId(1);
        recipe1.setIngredients(new ArrayList<>());
        recipe1.setInstructions("Instructions");
        recipe1.setName("Name");
        recipe1.setServings(1);
        recipe1.setType("Type");
        String content = (new ObjectMapper()).writeValueAsString(recipe1);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/recipes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(recipeController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"id\":1,\"name\":\"Name\",\"instructions\":\"Instructions\",\"servings\":1,\"type\":\"Type\",\"ingredients\":[]}"));
    }

    /**
     * Method under test: {@link RecipeController#updateRecipe(String, Recipe)}
     */
    @Test
    void testUpdateRecipe() throws Exception {
        Recipe recipe = new Recipe();
        recipe.setId(1);
        recipe.setIngredients(new ArrayList<>());
        recipe.setInstructions("Instructions");
        recipe.setName("Name");
        recipe.setServings(1);
        recipe.setType("Type");
        when(recipeService.updateRecipe((String) any(), (Recipe) any())).thenReturn(recipe);

        Recipe recipe1 = new Recipe();
        recipe1.setId(1);
        recipe1.setIngredients(new ArrayList<>());
        recipe1.setInstructions("Instructions");
        recipe1.setName("Name");
        recipe1.setServings(1);
        recipe1.setType("Type");
        String content = (new ObjectMapper()).writeValueAsString(recipe1);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put("/recipes/{name}", "Name")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(recipeController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"id\":1,\"name\":\"Name\",\"instructions\":\"Instructions\",\"servings\":1,\"type\":\"Type\",\"ingredients\":[]}"));
    }

    /**
     * Method under test: {@link RecipeController#deleteReceipe(String)}
     */
    @Test
    void testDeleteReceipe() throws Exception {
        when(recipeService.deleteReceipe((String) any())).thenReturn("Delete Receipe");
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/recipes/{name}", "Name");
        MockMvcBuilders.standaloneSetup(recipeController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=ISO-8859-1"))
                .andExpect(MockMvcResultMatchers.content().string("Delete Receipe"));
    }

    /**
     * Method under test: {@link RecipeController#deleteReceipe(String)}
     */
    @Test
    void testDeleteReceipe2() throws Exception {
        when(recipeService.deleteReceipe((String) any())).thenReturn("Delete Receipe");
        MockHttpServletRequestBuilder deleteResult = MockMvcRequestBuilders.delete("/recipes/{name}", "Name");
        deleteResult.characterEncoding("Encoding");
        MockMvcBuilders.standaloneSetup(recipeController)
                .build()
                .perform(deleteResult)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=ISO-8859-1"))
                .andExpect(MockMvcResultMatchers.content().string("Delete Receipe"));
    }

    /**
     * Method under test: {@link RecipeController#getRecipeByCondition(Recipe, String)}
     */
    @Test
    void testGetRecipeByCondition() throws Exception {
        when(recipeService.getRecipeByCondition((Recipe) any(), (String) any())).thenReturn(new ArrayList<>());

        Recipe recipe = new Recipe();
        recipe.setId(1);
        recipe.setIngredients(new ArrayList<>());
        recipe.setInstructions("Instructions");
        recipe.setName("Name");
        recipe.setServings(1);
        recipe.setType("Type");
        String content = (new ObjectMapper()).writeValueAsString(recipe);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/selected")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        MockMvcBuilders.standaloneSetup(recipeController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }
}

