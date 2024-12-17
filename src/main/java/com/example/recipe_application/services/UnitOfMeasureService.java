package com.example.recipe_application.services;
import com.example.recipe_application.commands.UnitOfMeasureCommand;

import java.util.Set;

public interface UnitOfMeasureService {
   Set<UnitOfMeasureCommand> listAllUoms();
}
