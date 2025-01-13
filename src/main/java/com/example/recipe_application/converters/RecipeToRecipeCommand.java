package com.example.recipe_application.converters;

import com.example.recipe_application.commands.RecipeCommand;
import com.example.recipe_application.domain.Category;
import com.example.recipe_application.domain.Recipe;
import jakarta.annotation.Nullable;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class RecipeToRecipeCommand implements Converter<Recipe, RecipeCommand> {

    private final CategoryToCategoryCommand categoryCommandConverter;
    private final IngredientToIngredientCommand ingredientCommandConverter;
    private final NotesToNotesCommand notesCommandConverter;

    public RecipeToRecipeCommand(CategoryToCategoryCommand categoryCommandConverter, IngredientToIngredientCommand ingredientCommandConverter, NotesToNotesCommand notesCommandConverter) {
        this.categoryCommandConverter = categoryCommandConverter;
        this.ingredientCommandConverter = ingredientCommandConverter;
        this.notesCommandConverter = notesCommandConverter;
    }


    @Synchronized
    @Nullable
    @Override
    public RecipeCommand convert(Recipe source) {
        if (source == null) {
            return null;
        }

        final RecipeCommand recipeCommand = new RecipeCommand();
        recipeCommand.setId(source.getId());
        recipeCommand.setDescription(source.getDescription());
        recipeCommand.setImage(source.getImages());
        recipeCommand.setCookTime(source.getCookTime());
        recipeCommand.setServings(source.getServings());
        recipeCommand.setUrl(source.getUrl());
        recipeCommand.setDirections(source.getDirections());
        recipeCommand.setSource(source.getSource());
        recipeCommand.setNotes(notesCommandConverter.convert(source.getNotes()));

        if (source.getCategories() != null && source.getCategories().size() > 0) {
            source.getCategories()
                    .forEach((Category category) -> recipeCommand.getCategories()
                            .add(categoryCommandConverter.convert(category)));
        }

        if (source.getIngredients() != null && source.getIngredients().size() > 0){
            source.getIngredients()
                    .forEach(ingredient -> recipeCommand.getIngredients().add(ingredientCommandConverter.convert(ingredient)));
        }
        return recipeCommand;
    }



//test

}
