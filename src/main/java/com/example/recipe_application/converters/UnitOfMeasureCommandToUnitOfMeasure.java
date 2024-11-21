package com.example.recipe_application.converters;

import com.example.recipe_application.commands.UnitOfMeasureCommand;
import com.example.recipe_application.domain.UnitOfMeasure;
import jakarta.annotation.Nullable;
import lombok.Synchronized;
import org.springframework.core.NestedExceptionUtils;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class UnitOfMeasureCommandToUnitOfMeasure implements Converter<UnitOfMeasureCommand, UnitOfMeasure> {
    @Synchronized
    @Nullable
    @Override
    public UnitOfMeasure convert(UnitOfMeasureCommand source) {
        if (source == null){
            return null;
        }

        final UnitOfMeasure unitOfMeasure = new UnitOfMeasure();
        unitOfMeasure.setId(source.getId());
        unitOfMeasure.setDescription(source.getDescription());
        return unitOfMeasure;
    }
}
