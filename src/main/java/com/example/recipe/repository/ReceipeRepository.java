package com.example.recipe.repository;

import com.example.recipe.entity.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

/**
 * Repository for Recipe
 */
@Transactional
@Repository
public interface ReceipeRepository extends JpaRepository<Recipe, Integer>, JpaSpecificationExecutor<Recipe> {

    Recipe findByName(String name);
}

