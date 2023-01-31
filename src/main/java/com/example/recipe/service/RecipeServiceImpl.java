package com.example.recipe.service;

import com.example.recipe.entity.Ingredient;
import com.example.recipe.entity.Recipe;
import com.example.recipe.repository.IngredientRepository;
import com.example.recipe.repository.ReceipeRepository;
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
                //Subquery<Recipe> subquery = query.subquery(Recipe.class);
                //Root<Ingredient> subRoot = subquery.from(Ingredient.class);
                //CriteriaQuery<Recipe> cq = criteriaBuilder.createQuery(Recipe.class);
                Root<Recipe> recipeRoot = query.from(Recipe.class);
                List<Predicate> predicates = new ArrayList<>();
                List<Predicate> subPredicates = new ArrayList<>();

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
                       Ingredient ingredient= ingredientRepository.findByName(ingredientName.getName());

                        Subquery<Long> subquery = query.subquery(Long.class);
                        Root<Recipe> subqueryRecipe = subquery.from(Recipe.class);
                        Join<Ingredient, Recipe> subqueryIngredient = subqueryRecipe.join("ingredients");

                        subquery.select(subqueryRecipe.get("id")).where(
                                criteriaBuilder.equal(subqueryIngredient.get("name"), ingredientName.getName()));

                        predicates.add(criteriaBuilder.in(recipeRoot.get("id")).value(subquery));
                    });
                }
                if (recipe.getIngredients() != null && ingredientCondition.equals("exclude")) {
                    recipe.getIngredients().stream().forEach(ingredientName -> {
                        //subPredicates.add(criteriaBuilder.notEqual(subRoot.get("name"), ingredientName.getName()));
                        //subquery.select(subRoot.get("id")).where(subPredicates.toArray(new Predicate[predicates.size()]));
                        //predicates.add(criteriaBuilder.and(criteriaBuilder.exists(subquery)));

                    });
                }
                //subquery.select(root.get("id")).where(subPredicates.toArray(new Predicate[predicates.size()]));
                //predicates.add(criteriaBuilder.and(criteriaBuilder.exists(subquery)));
                return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        });
    }


}

