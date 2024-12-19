package com.example.recipe_application.controllers;

import com.example.recipe_application.commands.IngredientCommand;
import com.example.recipe_application.commands.RecipeCommand;
import com.example.recipe_application.services.IngredientService;
import com.example.recipe_application.services.RecipeService;
import com.example.recipe_application.services.UnitOfMeasureService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Stream;

@Slf4j
@Controller
public class IngredientController {

    private final RecipeService recipeService;
    private final IngredientService ingredientService;
    private final UnitOfMeasureService unitOfMeasureService;


    public IngredientController(RecipeService recipeService, IngredientService ingredientService, UnitOfMeasureService unitOfMeasureService) {
        this.recipeService = recipeService;
        this.ingredientService = ingredientService;
        this.unitOfMeasureService = unitOfMeasureService;
    }


    @GetMapping
    @RequestMapping("/recipe/{id}/ingredients")
    public String listIngredient(@PathVariable String id, Model model){
        log.debug("getting recipe ingredient for recipe id: " + Long.valueOf(id));

        //use command object to avoid lazy load errors in Thymeleaf
        model.addAttribute("recipe", recipeService.findCommandById(Long.valueOf(id)));

        return "recipe/ingredient/list";
    }


    @GetMapping("recipe/{recipeId}/ingredient/{id}/show")
    public String showRecipeIngredient(@PathVariable String recipeId,
                                       @PathVariable String id, Model model){
        model.addAttribute("ingredient", ingredientService.findByRecipeIdAndIngredientId(Long.valueOf(recipeId), Long.valueOf(id)));

        return "recipe/ingredient/show";
    }


    @GetMapping
    @RequestMapping("recipe/{recipeId}/ingredient/{id}/update")
    public String updateIngredient(@PathVariable String recipeId, @PathVariable String id, Model model){
       log.debug("here is the ingredient update");
        model.addAttribute("ingredient", ingredientService.findByRecipeIdAndIngredientId(Long.valueOf(recipeId), Long.valueOf(id)));

        model.addAttribute("uomList", unitOfMeasureService.listAllUoms());
        return "recipe/ingredient/ingredientform";
    }

    @PostMapping("recipe/{recipeId}/ingredient")
    public String saveOrUpdate(@ModelAttribute IngredientCommand command){
        IngredientCommand savedCommand = ingredientService.saveIngredientCommand(command);

        log.debug("saved receipe id:" + savedCommand.getRecipeId());
        log.debug("saved ingredient id:" + savedCommand.getId());

        return "redirect:/recipe/" + savedCommand.getRecipeId() + "/ingredient/" + savedCommand.getId() + "/show";
    }


//    @PostMapping
//    @RequestMapping("/recipe/{recipeId}/ingredient")
//  public String saveOrUpdate(@ModelAttribute IngredientCommand ingredientCommand){
//        log.debug("her is ingredient save");
//
//        IngredientCommand savedIngredientCommand = ingredientService.saveIngredientCommand(ingredientCommand);
//
//        return  "redirect:/recipe/" + savedIngredientCommand.getRecipeId() + "/ingredient" + savedIngredientCommand.getId() + "/show";
//  }



}
