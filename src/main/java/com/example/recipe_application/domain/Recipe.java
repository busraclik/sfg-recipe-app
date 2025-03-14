package com.example.recipe_application.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

//@Data
@Entity
@Getter
@Setter
public class Recipe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String description;
    private Integer prepTime;
    private Integer cookTime;
    private Integer servings;
    private String url;
    private String source;
    @Lob
    @Column(columnDefinition = "LONGTEXT")
    private String directions;
    //todo add
   // private Difficulty difficulty;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "recipe" )
    private Set<Ingredient> ingredients = new HashSet<>();
    @Lob
    @Column(columnDefinition = "LONGBLOB")
    private Byte[] images;
    @Enumerated(value = EnumType.STRING)
    private Difficulty difficulty;

    @OneToOne(cascade = CascadeType.ALL)
    //@JoinColumn(name = "notes_id")
    private Notes notes;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "recipe_category",
    joinColumns = @JoinColumn(name = "recipe_id"),
    inverseJoinColumns = @JoinColumn(name = "category_id"))
    private Set<Category> categories = new HashSet<>();


    public void setNotes(Notes notes) {
        if (notes != null) {
            this.notes = notes;
            notes.setRecipe(this);
        }
//        this.notes = notes;
//        notes.setRecipe(this);
    }

    //helper method
    public Recipe addIngredient(Ingredient ingredient){
        ingredient.setRecipe(this);
        this.ingredients.add(ingredient);
        return this;
    }
}
