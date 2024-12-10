package com.example.recipe_application.controllers;

import com.example.recipe_application.commands.RecipeCommand;
import com.example.recipe_application.domain.Recipe;
import com.example.recipe_application.services.RecipeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


@Slf4j
@Controller
public class RecipeController {
    private final RecipeService recipeService;

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @RequestMapping("/recipe/{id}/show")
    public String showById(@PathVariable String id, Model model) {

        Optional<Recipe> recipeOpt = recipeService.findById(Long.valueOf(id));

        if (recipeOpt.isPresent()) {
            model.addAttribute("recipe", recipeOpt.get());
            log.info("Recipe details: {}", recipeOpt.get());
        } else {
            log.error("Recipe not found for ID: {}", id);
            model.addAttribute("error", "Recipe not found");
        }
        return "recipe/show";
    }

    @GetMapping
    @RequestMapping("/recipe/new")
    public String newRecipe(Model model){
        model.addAttribute("recipe", new RecipeCommand());
        return "recipe/recipeform";
    }

    @GetMapping
    @RequestMapping("/recipe/{id}/update")
    public String updateRecipe(@PathVariable String id, Model model){
        model.addAttribute("recipe", recipeService.findCommandById(Long.valueOf(id)));
        return "recipe/recipeform";
    }

    @PostMapping
    @RequestMapping("/recipe")
    public String saveOrUpdate(@ModelAttribute RecipeCommand recipeCommand){
        RecipeCommand savedCommand = recipeService.saveRecipeCommand(recipeCommand);
        return "redirect:/recipe/" + savedCommand.getId() + "/show";
    }

    @GetMapping
    @RequestMapping("/recipe/{id}/delete")
    public String deleteById(@PathVariable String id){
        log.debug("delete id : " + id);
        recipeService.deleteById(Long.valueOf(id));
        return "redirect:/";
    }



}
