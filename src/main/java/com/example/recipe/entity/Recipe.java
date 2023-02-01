package com.example.recipe.entity;

import lombok.*;

import javax.persistence.*;
import java.util.List;

/**
 * Entity class for Recipe
 */
@Entity
@Table(name = "recipe")
@Getter
@Setter
public class Recipe {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "receipe_sequence")
    @SequenceGenerator(name = "receipe_sequence", sequenceName = "receipe_sequence")
    private Integer id;

    private String name;

    private String instructions;

    private Integer servings;

    private String type;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Ingredient> ingredients;

}