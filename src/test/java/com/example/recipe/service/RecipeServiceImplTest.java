package com.example.recipe.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.recipe.entity.Recipe;
import com.example.recipe.mapper.DtoEntityMapper;
import com.example.recipe.model.RecipeDto;
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
    private DtoEntityMapper dtoEntityMapper;

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
        when(receipeRepository.findAll()).thenReturn(new ArrayList<>());
        assertTrue(recipeServiceImpl.getAllRecipes().isEmpty());
        verify(receipeRepository).findAll();
    }

    /**
     * Method under test: {@link RecipeServiceImpl#getAllRecipes()}
     */
    @Test
    void testGetAllRecipes2() {
        RecipeDto recipeDto = new RecipeDto();
        recipeDto.setIngredients(new ArrayList<>());
        recipeDto.setInstructions("Instructions");
        recipeDto.setName("Name");
        recipeDto.setServings(1);
        recipeDto.setType("Type");
        when(dtoEntityMapper.mapEntitytoDto((Recipe) any())).thenReturn(recipeDto);

        Recipe recipe = new Recipe();
        recipe.setId(1);
        recipe.setIngredients(new ArrayList<>());
        recipe.setInstructions("Instructions");
        recipe.setName("Name");
        recipe.setServings(1);
        recipe.setType("Type");

        ArrayList<Recipe> recipeList = new ArrayList<>();
        recipeList.add(recipe);
        when(receipeRepository.findAll()).thenReturn(recipeList);
        assertEquals(1, recipeServiceImpl.getAllRecipes().size());
        verify(dtoEntityMapper).mapEntitytoDto((Recipe) any());
        verify(receipeRepository).findAll();
    }

    /**
     * Method under test: {@link RecipeServiceImpl#getAllRecipes()}
     */
    @Test
    void testGetAllRecipes3() {
        RecipeDto recipeDto = new RecipeDto();
        recipeDto.setIngredients(new ArrayList<>());
        recipeDto.setInstructions("Instructions");
        recipeDto.setName("Name");
        recipeDto.setServings(1);
        recipeDto.setType("Type");
        when(dtoEntityMapper.mapEntitytoDto((Recipe) any())).thenReturn(recipeDto);

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

        ArrayList<Recipe> recipeList = new ArrayList<>();
        recipeList.add(recipe1);
        recipeList.add(recipe);
        when(receipeRepository.findAll()).thenReturn(recipeList);
        assertEquals(2, recipeServiceImpl.getAllRecipes().size());
        verify(dtoEntityMapper, atLeast(1)).mapEntitytoDto((Recipe) any());
        verify(receipeRepository).findAll();
    }

    /**
     * Method under test: {@link RecipeServiceImpl#getRecipeByCondition(RecipeDto, String)}
     */
    @Test
    void testGetRecipeByCondition() {
        Recipe recipe = new Recipe();
        recipe.setId(1);
        recipe.setIngredients(new ArrayList<>());
        recipe.setInstructions("Instructions");
        recipe.setName("Name");
        recipe.setServings(1);
        recipe.setType("Type");
        when(dtoEntityMapper.mapDtoToEntity((RecipeDto) any())).thenReturn(recipe);
        when(receipeRepository.findAll((Specification<Recipe>) any())).thenReturn(new ArrayList<>());

        RecipeDto recipeDto = new RecipeDto();
        recipeDto.setIngredients(new ArrayList<>());
        recipeDto.setInstructions("Instructions");
        recipeDto.setName("Name");
        recipeDto.setServings(1);
        recipeDto.setType("Type");
        assertTrue(recipeServiceImpl.getRecipeByCondition(recipeDto, "Ingredient Condition").isEmpty());
        verify(dtoEntityMapper).mapDtoToEntity((RecipeDto) any());
        verify(receipeRepository).findAll((Specification<Recipe>) any());
    }

    /**
     * Method under test: {@link RecipeServiceImpl#getRecipeByCondition(RecipeDto, String)}
     */
    @Test
    void testGetRecipeByCondition2() {
        Recipe recipe = new Recipe();
        recipe.setId(1);
        recipe.setIngredients(new ArrayList<>());
        recipe.setInstructions("Instructions");
        recipe.setName("Name");
        recipe.setServings(1);
        recipe.setType("Type");

        RecipeDto recipeDto = new RecipeDto();
        recipeDto.setIngredients(new ArrayList<>());
        recipeDto.setInstructions("Instructions");
        recipeDto.setName("Name");
        recipeDto.setServings(1);
        recipeDto.setType("Type");
        when(dtoEntityMapper.mapEntitytoDto((Recipe) any())).thenReturn(recipeDto);
        when(dtoEntityMapper.mapDtoToEntity((RecipeDto) any())).thenReturn(recipe);

        Recipe recipe1 = new Recipe();
        recipe1.setId(1);
        recipe1.setIngredients(new ArrayList<>());
        recipe1.setInstructions("Instructions");
        recipe1.setName("Name");
        recipe1.setServings(1);
        recipe1.setType("Type");

        ArrayList<Recipe> recipeList = new ArrayList<>();
        recipeList.add(recipe1);
        when(receipeRepository.findAll((Specification<Recipe>) any())).thenReturn(recipeList);

        RecipeDto recipeDto1 = new RecipeDto();
        recipeDto1.setIngredients(new ArrayList<>());
        recipeDto1.setInstructions("Instructions");
        recipeDto1.setName("Name");
        recipeDto1.setServings(1);
        recipeDto1.setType("Type");
        assertEquals(1, recipeServiceImpl.getRecipeByCondition(recipeDto1, "Ingredient Condition").size());
        verify(dtoEntityMapper).mapDtoToEntity((RecipeDto) any());
        verify(dtoEntityMapper).mapEntitytoDto((Recipe) any());
        verify(receipeRepository).findAll((Specification<Recipe>) any());
    }

    /**
     * Method under test: {@link RecipeServiceImpl#getRecipeByCondition(RecipeDto, String)}
     */
    @Test
    void testGetRecipeByCondition3() {
        Recipe recipe = new Recipe();
        recipe.setId(1);
        recipe.setIngredients(new ArrayList<>());
        recipe.setInstructions("Instructions");
        recipe.setName("Name");
        recipe.setServings(1);
        recipe.setType("Type");

        RecipeDto recipeDto = new RecipeDto();
        recipeDto.setIngredients(new ArrayList<>());
        recipeDto.setInstructions("Instructions");
        recipeDto.setName("Name");
        recipeDto.setServings(1);
        recipeDto.setType("Type");
        when(dtoEntityMapper.mapEntitytoDto((Recipe) any())).thenReturn(recipeDto);
        when(dtoEntityMapper.mapDtoToEntity((RecipeDto) any())).thenReturn(recipe);

        Recipe recipe1 = new Recipe();
        recipe1.setId(1);
        recipe1.setIngredients(new ArrayList<>());
        recipe1.setInstructions("Instructions");
        recipe1.setName("Name");
        recipe1.setServings(1);
        recipe1.setType("Type");

        Recipe recipe2 = new Recipe();
        recipe2.setId(1);
        recipe2.setIngredients(new ArrayList<>());
        recipe2.setInstructions("Instructions");
        recipe2.setName("Name");
        recipe2.setServings(1);
        recipe2.setType("Type");

        ArrayList<Recipe> recipeList = new ArrayList<>();
        recipeList.add(recipe2);
        recipeList.add(recipe1);
        when(receipeRepository.findAll((Specification<Recipe>) any())).thenReturn(recipeList);

        RecipeDto recipeDto1 = new RecipeDto();
        recipeDto1.setIngredients(new ArrayList<>());
        recipeDto1.setInstructions("Instructions");
        recipeDto1.setName("Name");
        recipeDto1.setServings(1);
        recipeDto1.setType("Type");
        assertEquals(2, recipeServiceImpl.getRecipeByCondition(recipeDto1, "Ingredient Condition").size());
        verify(dtoEntityMapper).mapDtoToEntity((RecipeDto) any());
        verify(dtoEntityMapper, atLeast(1)).mapEntitytoDto((Recipe) any());
        verify(receipeRepository).findAll((Specification<Recipe>) any());
    }

    /**
     * Method under test: {@link RecipeServiceImpl#addRecipe(RecipeDto)}
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

        RecipeDto recipeDto = new RecipeDto();
        recipeDto.setIngredients(new ArrayList<>());
        recipeDto.setInstructions("Instructions");
        recipeDto.setName("Name");
        recipeDto.setServings(1);
        recipeDto.setType("Type");
        when(dtoEntityMapper.mapDtoToEntity((RecipeDto) any())).thenReturn(recipe);
        when(dtoEntityMapper.mapEntitytoDto((Recipe) any())).thenReturn(recipeDto);

        Recipe recipe1 = new Recipe();
        recipe1.setId(1);
        recipe1.setIngredients(new ArrayList<>());
        recipe1.setInstructions("Instructions");
        recipe1.setName("Name");
        recipe1.setServings(1);
        recipe1.setType("Type");
        when(receipeRepository.save((Recipe) any())).thenReturn(recipe1);

        RecipeDto recipeDto1 = new RecipeDto();
        recipeDto1.setIngredients(new ArrayList<>());
        recipeDto1.setInstructions("Instructions");
        recipeDto1.setName("Name");
        recipeDto1.setServings(1);
        recipeDto1.setType("Type");
        assertSame(recipeDto, recipeServiceImpl.addRecipe(recipeDto1));
        verify(dtoEntityMapper).mapDtoToEntity((RecipeDto) any());
        verify(dtoEntityMapper).mapEntitytoDto((Recipe) any());
        verify(receipeRepository).save((Recipe) any());
    }

    /**
     * Method under test: {@link RecipeServiceImpl#updateRecipe(String, RecipeDto)}
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

        RecipeDto recipeDto = new RecipeDto();
        recipeDto.setIngredients(new ArrayList<>());
        recipeDto.setInstructions("Instructions");
        recipeDto.setName("Name");
        recipeDto.setServings(1);
        recipeDto.setType("Type");
        when(dtoEntityMapper.mapDtoToEntity((RecipeDto) any())).thenReturn(recipe);
        when(dtoEntityMapper.mapEntitytoDto((Recipe) any())).thenReturn(recipeDto);

        Recipe recipe1 = new Recipe();
        recipe1.setId(1);
        recipe1.setIngredients(new ArrayList<>());
        recipe1.setInstructions("Instructions");
        recipe1.setName("Name");
        recipe1.setServings(1);
        recipe1.setType("Type");

        Recipe recipe2 = new Recipe();
        recipe2.setId(1);
        recipe2.setIngredients(new ArrayList<>());
        recipe2.setInstructions("Instructions");
        recipe2.setName("Name");
        recipe2.setServings(1);
        recipe2.setType("Type");
        when(receipeRepository.findByName((String) any())).thenReturn(recipe1);
        when(receipeRepository.save((Recipe) any())).thenReturn(recipe2);

        RecipeDto recipeDto1 = new RecipeDto();
        recipeDto1.setIngredients(new ArrayList<>());
        recipeDto1.setInstructions("Instructions");
        recipeDto1.setName("Name");
        recipeDto1.setServings(1);
        recipeDto1.setType("Type");
        assertSame(recipeDto, recipeServiceImpl.updateRecipe("Name", recipeDto1));
        verify(dtoEntityMapper).mapDtoToEntity((RecipeDto) any());
        verify(dtoEntityMapper).mapEntitytoDto((Recipe) any());
        verify(receipeRepository).findByName((String) any());
        verify(receipeRepository).save((Recipe) any());
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

