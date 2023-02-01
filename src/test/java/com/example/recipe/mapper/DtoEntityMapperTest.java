package com.example.recipe.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.recipe.entity.Ingredient;
import com.example.recipe.entity.Recipe;
import com.example.recipe.model.RecipeDto;
import com.example.recipe.repository.IngredientRepository;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {DtoEntityMapper.class})
@ExtendWith(SpringExtension.class)
class DtoEntityMapperTest {
    @Autowired
    private DtoEntityMapper dtoEntityMapper;

    @MockBean
    private IngredientRepository ingredientRepository;

    /**
     * Method under test: {@link DtoEntityMapper#mapEntitytoDto(Recipe)}
     */
    @Test
    void testMapEntitytoDto() {
        Recipe recipe = new Recipe();
        recipe.setId(1);
        ArrayList<Ingredient> ingredientList = new ArrayList<>();
        recipe.setIngredients(ingredientList);
        recipe.setInstructions("Instructions");
        recipe.setName("Name");
        recipe.setServings(1);
        recipe.setType("Type");
        RecipeDto actualMapEntitytoDtoResult = dtoEntityMapper.mapEntitytoDto(recipe);
        assertEquals(ingredientList, actualMapEntitytoDtoResult.getIngredients());
        assertEquals("Type", actualMapEntitytoDtoResult.getType());
        assertEquals(1, actualMapEntitytoDtoResult.getServings().intValue());
        assertEquals("Name", actualMapEntitytoDtoResult.getName());
        assertEquals("Instructions", actualMapEntitytoDtoResult.getInstructions());
    }

    /**
     * Method under test: {@link DtoEntityMapper#mapDtoToEntity(RecipeDto)}
     */
    @Test
    void testMapDtoToEntity() {
        RecipeDto recipeDto = new RecipeDto();
        ArrayList<String> stringList = new ArrayList<>();
        recipeDto.setIngredients(stringList);
        recipeDto.setInstructions("Instructions");
        recipeDto.setName("Name");
        recipeDto.setServings(1);
        recipeDto.setType("Type");
        Recipe actualMapDtoToEntityResult = dtoEntityMapper.mapDtoToEntity(recipeDto);
        assertEquals("Type", actualMapDtoToEntityResult.getType());
        assertEquals(1, actualMapDtoToEntityResult.getServings().intValue());
        assertEquals("Name", actualMapDtoToEntityResult.getName());
        assertEquals("Instructions", actualMapDtoToEntityResult.getInstructions());
        assertEquals(stringList, actualMapDtoToEntityResult.getIngredients());
    }

    /**
     * Method under test: {@link DtoEntityMapper#mapDtoToEntity(RecipeDto)}
     */
    @Test
    void testMapDtoToEntity2() {
        Ingredient ingredient = new Ingredient();
        ingredient.setId(1);
        ingredient.setName("Name");
        when(ingredientRepository.findByName((String) any())).thenReturn(ingredient);

        ArrayList<String> stringList = new ArrayList<>();
        stringList.add("foo");

        RecipeDto recipeDto = new RecipeDto();
        recipeDto.setIngredients(stringList);
        recipeDto.setInstructions("Instructions");
        recipeDto.setName("Name");
        recipeDto.setServings(1);
        recipeDto.setType("Type");
        Recipe actualMapDtoToEntityResult = dtoEntityMapper.mapDtoToEntity(recipeDto);
        assertEquals("Type", actualMapDtoToEntityResult.getType());
        assertEquals(1, actualMapDtoToEntityResult.getServings().intValue());
        assertEquals("Name", actualMapDtoToEntityResult.getName());
        assertEquals("Instructions", actualMapDtoToEntityResult.getInstructions());
        List<Ingredient> ingredients = actualMapDtoToEntityResult.getIngredients();
        assertEquals(1, ingredients.size());
        assertEquals("foo", ingredients.get(0).getName());
        verify(ingredientRepository).findByName((String) any());
    }

    /**
     * Method under test: {@link DtoEntityMapper#mapDtoToEntity(RecipeDto)}
     */
    @Test
    void testMapDtoToEntity3() {
        Ingredient ingredient = new Ingredient();
        ingredient.setId(1);
        ingredient.setName("Name");
        when(ingredientRepository.findByName((String) any())).thenReturn(ingredient);

        ArrayList<String> stringList = new ArrayList<>();
        stringList.add("foo");
        stringList.add("foo");

        RecipeDto recipeDto = new RecipeDto();
        recipeDto.setIngredients(stringList);
        recipeDto.setInstructions("Instructions");
        recipeDto.setName("Name");
        recipeDto.setServings(1);
        recipeDto.setType("Type");
        Recipe actualMapDtoToEntityResult = dtoEntityMapper.mapDtoToEntity(recipeDto);
        assertEquals("Type", actualMapDtoToEntityResult.getType());
        assertEquals(1, actualMapDtoToEntityResult.getServings().intValue());
        assertEquals("Name", actualMapDtoToEntityResult.getName());
        assertEquals("Instructions", actualMapDtoToEntityResult.getInstructions());
        List<Ingredient> ingredients = actualMapDtoToEntityResult.getIngredients();
        assertEquals(2, ingredients.size());
        assertEquals("foo", ingredients.get(0).getName());
        verify(ingredientRepository, atLeast(1)).findByName((String) any());
    }
}

