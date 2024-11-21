package com.example.recipe_application.converters;

import com.example.recipe_application.commands.NotesCommand;
import com.example.recipe_application.domain.Notes;
import jakarta.annotation.Nullable;
import lombok.Synchronized;
import org.hibernate.annotations.SQLUpdate;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class NotesToNotesCommand implements Converter<Notes, NotesCommand> {
    @Synchronized
    @Nullable
    @Override
    public NotesCommand convert(Notes source) {
        if (source == null){
            return null;
        }

        final NotesCommand notesCommand = new NotesCommand();
        notesCommand.setId(source.getId());
        notesCommand.setRecipeNotes(source.getRecipeNotes());
        return notesCommand;
    }
}
