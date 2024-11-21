package com.example.recipe_application.converters;


import com.example.recipe_application.commands.IngredientCommand;
import com.example.recipe_application.domain.Ingredient;
import com.mysql.cj.protocol.a.NativeUtils;
import jakarta.annotation.Nullable;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class IngredientToIngredientCommand implements Converter<Ingredient, IngredientCommand> {

    private final UnitOfMeasureToUnitOfMeasureCommand uomConverter;

    public IngredientToIngredientCommand(UnitOfMeasureToUnitOfMeasureCommand uomConverter) {
        this.uomConverter = uomConverter;
    }

      @Synchronized
      @Nullable
      @Override
    public IngredientCommand convert(Ingredient source) {
          if (source == null) {
              return null;
          }

          IngredientCommand ingredientCommand = new IngredientCommand();
          ingredientCommand.setId(source.getId());
          if (source.getRecipe() != null){
              ingredientCommand.setRecipeId(source.getRecipe().getId());
          }
          ingredientCommand.setAmount(source.getAmount());
          ingredientCommand.setDescription(source.getDescription());
          ingredientCommand.setUom(uomConverter.convert(source.getUnitOfMeasure()));
          return ingredientCommand;
    }
}
