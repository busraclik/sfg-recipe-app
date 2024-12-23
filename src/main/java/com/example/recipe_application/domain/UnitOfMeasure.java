package com.example.recipe_application.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

//@Data
@Getter
@Setter
@Entity
public class UnitOfMeasure {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String description;
//    @OneToOne(mappedBy = "unitOfMeasure")
//    private Ingredient ingredient;

    @OneToMany(mappedBy = "unitOfMeasure")
    private Set<Ingredient> ingredients = new HashSet<>();

}
