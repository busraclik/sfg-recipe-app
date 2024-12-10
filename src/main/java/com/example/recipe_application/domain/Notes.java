package com.example.recipe_application.domain;

import jakarta.persistence.*;
import lombok.*;

//@Data
@Getter
@Setter
@EqualsAndHashCode(exclude ={"recipe"})
@Entity
public class Notes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(mappedBy = "notes")
    //@JoinColumn(name = "recipe_id")
    private Recipe recipe;

    @Lob
    private String recipeNotes;

}
