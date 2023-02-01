package com.example.recipe.service;

import com.example.recipe.entity.Ingredient;
import com.example.recipe.entity.Recipe;
import com.example.recipe.mapper.DtoEntityMapper;
import com.example.recipe.model.RecipeDto;
import com.example.recipe.repository.IngredientRepository;
import com.example.recipe.repository.ReceipeRepository;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

/**
 * service class for recipe application
 */
@Service
public class RecipeServiceImpl implements RecipeService {
    @Resource
    private ReceipeRepository receipeRepository;
    @Resource
    private IngredientRepository ingredientRepository;
    @Resource
    private DtoEntityMapper dtoEntityMapper;


    /**
     * Method to get all recipe
     *
     * @return List<Recipe> List of the all the recipe present in database
     */
    @Override
    public List<RecipeDto> getAllRecipes() {
        List<RecipeDto> recipeDtos =new ArrayList<>();
        List<Recipe> recipes = receipeRepository.findAll();
        recipes.stream().forEach(recipe -> {
            RecipeDto recipeDto = dtoEntityMapper.mapEntitytoDto(recipe);
            recipeDtos.add(recipeDto);
        });
        return recipeDtos;
    }

    /**
     * Method to get recipe based on selected condition
     *
     * @param recipe              The recipe object containg filter condition
     * @param ingredientCondition condition saying if the ingredient mentioned need to be included or excluded
     * @return List<Recipe> List of the all the recipe with given filter
     */
    @Override
    public List<RecipeDto> getRecipeByCondition(RecipeDto recipe, String ingredientCondition) {
        List<RecipeDto> recipeDtos =new ArrayList<>();
        Recipe recipeEntity = dtoEntityMapper.mapDtoToEntity(recipe);
        List<Recipe> recipes = findByCriteria(recipeEntity, ingredientCondition);
        recipes.stream().forEach(recipemap -> {
            RecipeDto recipeDto = dtoEntityMapper.mapEntitytoDto(recipemap);
            recipeDtos.add(recipeDto);
        });
        return recipeDtos;
    }

    /**
     * Method to add recipe
     *
     * @param recipedto the recipe to be added
     * @return Recipe added recipe
     */
    @Override
    public RecipeDto addRecipe(RecipeDto recipedto) {
       Recipe recipe= receipeRepository.save(dtoEntityMapper.mapDtoToEntity(recipedto));
        return dtoEntityMapper.mapEntitytoDto(recipe);
    }

    /**
     * Method to update recipe
     *
     * @param name   name of recipe to be updated
     * @param recipeDto the recipe to be added
     * @return Recipe updated recipe
     */
    @Override
    public RecipeDto updateRecipe(String name, RecipeDto recipeDto) {
        Recipe recipe = dtoEntityMapper.mapDtoToEntity(recipeDto);
        Recipe recipeName = receipeRepository.findByName(name);
        if (null != recipeName)
            recipe.setId(recipeName.getId());
        Recipe updatedRecipe = receipeRepository.save(recipe);
        return dtoEntityMapper.mapEntitytoDto(updatedRecipe);
    }

    /**
     * Method to delete Recipe
     *
     * @param name of recipe to be deleted
     * @return String stating deletion successful
     */
    @Override
    public String deleteReceipe(String name) {
        Recipe recipe = receipeRepository.findByName(name);
        receipeRepository.deleteById(recipe.getId());
        return "Recipe " + name + " deleted successfully!";
    }

    public List<Recipe> findByCriteria(Recipe recipe, String ingredientCondition) {
        return receipeRepository.findAll(new Specification<Recipe>() {
            @Override
            public Predicate toPredicate(Root<Recipe> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();
                List<Predicate> ingredientPredicates = new ArrayList<>();
                Join<Recipe, Ingredient> hnJoin = root.join("ingredients");

                if (recipe.getInstructions() != null) {
                    predicates.add(criteriaBuilder.and(criteriaBuilder.like(root.get("instructions"), "%" + recipe.getInstructions() + "%")));
                }
                if (recipe.getType() != null) {
                    predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("type"), recipe.getType())));
                }
                if (recipe.getName() != null) {
                    predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("name"), recipe.getName())));
                }
                if (recipe.getServings() != null) {
                    predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("servings"), recipe.getServings())));
                }
                if (recipe.getIngredients() != null && ingredientCondition.equals("include")) {
                    recipe.getIngredients().stream().forEach(ingredientName -> {
                        if (null != ingredientName && StringUtils.isNotBlank(ingredientName.getName())) {
                                ingredientPredicates.add(criteriaBuilder.equal(hnJoin.get("name"), ingredientName.getName()));
                            }
                    });
                    predicates.add(criteriaBuilder.and(ingredientPredicates.toArray(new Predicate[]{})));

                }
                if (recipe.getIngredients() != null && ingredientCondition.equals("exclude")) {
                    recipe.getIngredients().stream().forEach(ingredientName -> {
                        if (null != ingredientName && StringUtils.isNotBlank(ingredientName.getName())) {{
                                ingredientPredicates.add(criteriaBuilder.not(hnJoin.get("name").in(ingredientName.getName())));
                            }
                        }
                    });

                    predicates.add(criteriaBuilder.and(ingredientPredicates.toArray(new Predicate[]{})));
                }
                return criteriaBuilder.and(predicates.toArray(new Predicate[]{}));
            }
        });
    }


}

