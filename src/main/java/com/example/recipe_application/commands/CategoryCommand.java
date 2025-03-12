package com.example.recipe_application.commands;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CategoryCommand {
    private Long id;
    private String description;

    // ID'yi alabilen bir constructor
    public CategoryCommand(Long id) {
        this.id = id;
    }

}
