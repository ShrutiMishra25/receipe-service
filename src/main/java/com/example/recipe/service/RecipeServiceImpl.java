package com.example.recipe.service;

import com.example.recipe.entity.Ingredient;
import com.example.recipe.entity.Recipe;
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


    /**
     * Method to get all recipe
     *
     * @return List<Recipe> List of the all the recipe present in database
     */
    @Override
    public List<Recipe> getAllRecipes() {
        List<Recipe> recipes = receipeRepository.findAll();
        return recipes;
    }

    /**
     * Method to get recipe based on selected condition
     *
     * @param Recipe              The recipe object containg filter condition
     * @param ingredientCondition condition saying if the ingredient mentioned need to be included or excluded
     * @return List<Recipe> List of the all the recipe with given filter
     */
    @Override
    public List<Recipe> getRecipeByCondition(Recipe Recipe, String ingredientCondition) {
        List<Recipe> recipes = findByCriteria(Recipe, ingredientCondition);
        return recipes;
    }

    /**
     * Method to add recipe
     *
     * @param recipe the recipe to be added
     * @return Recipe added recipe
     */
    @Override
    public Recipe addRecipe(Recipe recipe) {
        return receipeRepository.save(recipe);
    }

    /**
     * Method to update recipe
     *
     * @param name   name of recipe to be updated
     * @param recipe the recipe to be added
     * @return Recipe updated recipe
     */
    @Override
    public Recipe updateRecipe(String name, Recipe recipe) {
        Recipe recipeName = receipeRepository.findByName(name);
        if (null != recipeName)
            recipe.setId(recipeName.getId());

        Recipe updatedRecipe = receipeRepository.save(recipe);
        return updatedRecipe;
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
        //Remove the related ingredients from recipe entity.
        receipeRepository.deleteById(recipe.getId());
        return "Recipe " + name + " deleted successfully!";
    }

    public List<Recipe> findByCriteria(Recipe recipe, String ingredientCondition) {
        return receipeRepository.findAll(new Specification<Recipe>() {
            @Override
            public Predicate toPredicate(Root<Recipe> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {

                List<Predicate> predicates = new ArrayList<>();
                List<Predicate> ingredientPredicates = new ArrayList<Predicate>();
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
                        if (null != ingredientName) {
                            if (StringUtils.isNotBlank(ingredientName.getName())) {
                                ingredientPredicates.add(criteriaBuilder.equal(hnJoin.get("name"), ingredientName.getName()));
                            }
                        }
                    });
                    predicates.add(criteriaBuilder.and(ingredientPredicates.toArray(new Predicate[]{})));

                }
                if (recipe.getIngredients() != null && ingredientCondition.equals("exclude")) {
                    recipe.getIngredients().stream().forEach(ingredientName -> {
                        if (null != ingredientName) {
                            if (StringUtils.isNotBlank(ingredientName.getName())) {
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

