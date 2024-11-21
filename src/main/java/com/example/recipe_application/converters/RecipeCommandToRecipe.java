package com.example.recipe_application.converters;

import com.example.recipe_application.commands.RecipeCommand;
import com.example.recipe_application.domain.Recipe;
import jakarta.annotation.Nullable;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;


@Component
public class RecipeCommandToRecipe implements Converter<RecipeCommand, Recipe> {
    private final CategoryCommandToCategory categoryConverter;
    private final IngredientCommandToIngredient ingredientConverter;
    private final NotesCommandToNotes notesConverter;

    public RecipeCommandToRecipe(CategoryCommandToCategory categoryConverter, IngredientCommandToIngredient ingredientConverter, NotesCommandToNotes notesConverter) {
        this.categoryConverter = categoryConverter;
        this.ingredientConverter = ingredientConverter;
        this.notesConverter = notesConverter;
    }


    @Synchronized
    @Nullable
    @Override
    public Recipe convert(RecipeCommand source) {
        if (source == null){
            return null;
        }

        final Recipe recipe = new Recipe();
        recipe.setId(source.getId());
        recipe.setDescription(source.getDescription());
        recipe.setCookTime(source.getCookTime());
        recipe.setPrepTime(source.getPrepTime());
        recipe.setImages(source.getImage());
        recipe.setSource(source.getSource());
        recipe.setUrl(source.getUrl());
        recipe.setNotes(notesConverter.convert(source.getNotes()));

        if (source.getCategories() != null && source.getCategories().size() >0){
            source.getCategories()
                    .forEach(categoryCommand -> recipe.getCategories()
                    .add(categoryConverter.convert(categoryCommand)));
        }

        if (source.getIngredients() != null && source.getIngredients().size() > 0){
            source.getIngredients().forEach(ingredientCommand -> recipe.getIngredients()
                    .add(ingredientConverter.convert(ingredientCommand)));
        }

        return recipe;
    }
}
