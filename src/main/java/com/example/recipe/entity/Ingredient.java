package com.example.recipe.entity;

import lombok.*;

import javax.persistence.*;

/**
 * Entity class for ingredient
 */
@Entity
@Getter
@Setter
@Table(name = "ingredient")
@ToString
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class Ingredient {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ingredient_sequence")
    @SequenceGenerator(name = "ingredient_sequence", sequenceName = "ingredient_sequence")
    private Integer id;
    @Column(name = "name")
    private String name;

}
