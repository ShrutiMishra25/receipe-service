package com.example.recipe.repository;

import com.example.recipe.entity.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Repository for Ingredient
 */
@Transactional
@Repository
public interface IngredientRepository extends JpaRepository<Ingredient, Integer> {
    Ingredient findByName(String name);

}
