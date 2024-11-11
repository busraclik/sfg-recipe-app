package com.example.recipe_application.bootstrap;

import com.example.recipe_application.domain.*;
import com.example.recipe_application.repository.CategoryRepository;
import com.example.recipe_application.repository.RecipeRepository;
import com.example.recipe_application.repository.UniteOfMeasureRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;

import static java.rmi.server.LogStream.log;

@Slf4j
@Component
public class RecipeBootstrap implements ApplicationListener<ContextRefreshedEvent> {

    private final CategoryRepository categoryRepository;
    private final RecipeRepository recipeRepository;
    private final UniteOfMeasureRepository uniteOfMeasureRepository;

    public RecipeBootstrap(CategoryRepository categoryRepository, RecipeRepository recipeRepository, UniteOfMeasureRepository uniteOfMeasureRepository) {
        this.categoryRepository = categoryRepository;
        this.recipeRepository = recipeRepository;
        this.uniteOfMeasureRepository = uniteOfMeasureRepository;
    }


    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent event) {
//
//        recipeRepository.deleteAll();
//        recipeRepository.saveAll(getRecipes());
//        log.debug("loading bootstrap data");
//////
        if (recipeRepository.count() == 0) {
            recipeRepository.saveAll(getRecipes());
            log.debug("loading bootstrap data");
        }

    }

    private List<Recipe> getRecipes(){

        List<Recipe> recipes = new ArrayList<>(2);

        //get uom
        Optional<UnitOfMeasure> eachUnitOfMeasure = uniteOfMeasureRepository.findByDescription("Each");
        if (!eachUnitOfMeasure.isPresent()){
            throw new RuntimeException("Expected UOM not found");
        }

        Optional<UnitOfMeasure> tableSpoonUnitOfMeasure= uniteOfMeasureRepository.findByDescription("Tablespoon");
        if (!tableSpoonUnitOfMeasure.isPresent()){
            throw new RuntimeException("Expected UOM not found");
        }

        Optional<UnitOfMeasure> teaspoonUnitOfMeasure = uniteOfMeasureRepository.findByDescription("Teaspoon");
        if (!teaspoonUnitOfMeasure.isPresent()){
            throw new RuntimeException("Expected UOM not found");
        }

        Optional<UnitOfMeasure> dashUnitOfMeasure = uniteOfMeasureRepository.findByDescription("Dash");
        if (!dashUnitOfMeasure.isPresent()){
            throw new RuntimeException("Expected UOM not found");
        }

        Optional<UnitOfMeasure> pintUniteOfMeasure= uniteOfMeasureRepository.findByDescription("Pint");
        if (!pintUniteOfMeasure.isPresent()){
            throw new RuntimeException("Expected UOM not found");
        }

        Optional<UnitOfMeasure> cupUniteOfMeasure = uniteOfMeasureRepository.findByDescription("Cup");
        if (!cupUniteOfMeasure.isPresent()){
            throw new RuntimeException("Expected UOM not found");
        }


        //get optionals
        UnitOfMeasure eachUom = eachUnitOfMeasure.get();
        UnitOfMeasure tableSpoonUom = tableSpoonUnitOfMeasure.get();
        UnitOfMeasure teaSpoonUom = teaspoonUnitOfMeasure.get();
        UnitOfMeasure dashUom = dashUnitOfMeasure.get();
        UnitOfMeasure pintUom = pintUniteOfMeasure.get();
        UnitOfMeasure cupUom = cupUniteOfMeasure.get();


        //get categories
        Optional<Category> americanCategoryOptional = categoryRepository.findByDescription("American");
        if (!americanCategoryOptional.isPresent()){
            throw new RuntimeException("Expected category not found");
        }

        Optional<Category> mexicanCategoryOtional = categoryRepository.findByDescription("Mexican");
        if (!mexicanCategoryOtional.isPresent()){
            throw new RuntimeException("Expected category not found");
        }

        Category americanCategory = americanCategoryOptional.get();
        Category mexicanCategory = mexicanCategoryOtional.get();

        //yummy guaca
        Recipe guacRecipe = new Recipe();
        guacRecipe.setDescription("yummy guaca");
        guacRecipe.setPrepTime(5);
        guacRecipe.setCookTime(7);
        guacRecipe.setDifficulty(Difficulty.EASY);
        guacRecipe.setDirections("here is the example of description of yummy guace recipe" +
                "1)cut patatoes" +
                "\n" +
                "2)fries the pataotoes" +
                "\n" +
                "2)here is the test" +
                "\n" +
                "for more : www.tarif.com"
        );

        Notes guacNotes = new Notes();
        guacNotes.setRecipeNotes("here is the notes for this meal" +
                "this is just for test" +
                "if you want to make it clear" +
                "for more: www.here.com");
        guacNotes.setRecipe(guacRecipe);
        guacRecipe.setNotes(guacNotes);

        guacRecipe.addIngredient(new Ingredient("Avocado",new BigDecimal(2), eachUom));
        guacRecipe.addIngredient(new Ingredient("Salmon", new BigDecimal(1), pintUom));
        guacRecipe.addIngredient(new Ingredient("Fresh Lime Juice", new BigDecimal(5), tableSpoonUom));
        guacRecipe.addIngredient(new Ingredient("Salt", new BigDecimal(1),teaSpoonUom));

        guacRecipe.getCategories().add(americanCategory);
//        guacRecipe.getCategories().add(mexicanCategory);

        //return to recepies
        recipes.add(guacRecipe);


        //second recipe for lemonade
        Recipe lemonadeRecipe = new Recipe();
        lemonadeRecipe.setDescription("Iced Lemonade");
        lemonadeRecipe.setPrepTime(2);
        lemonadeRecipe.setCookTime(1);
        lemonadeRecipe.setDifficulty(Difficulty.MODERATE);
        lemonadeRecipe.setDirections("here is the lemonade directions: " +
                "1)Peel the lemonds" +
                "\n" +
                "2)squezz the lemonades" +
                "\n" +
                "3)add ices and water" +
                "\n" +
                "4)add some sugar and mint" +
                "\n" +
                "for more information: www.lemonde.com");
        Notes lemonadeNote = new Notes();
        lemonadeNote.setRecipeNotes("here is the lemonde notes: be sure that the lemons are good" +
                "its same for mints" +
                "if its not its not good for taste" +
                "for more info www.lemonades.com");
        lemonadeNote.setRecipe(lemonadeRecipe);
        lemonadeRecipe.setNotes(lemonadeNote);

        lemonadeRecipe.addIngredient(new Ingredient("Lemon", new BigDecimal(3), dashUom));
        lemonadeRecipe.addIngredient(new Ingredient("Sugar", new BigDecimal(1), cupUom));
       // lemonadeRecipe.addIngredient(new Ingredient("Ice", new BigDecimal(2), eachUom));
//        lemonadeRecipe.addIngredient(new Ingredient("salt", new BigDecimal(5), tableSpoonUom));
        lemonadeRecipe.getCategories().add(americanCategory);
        lemonadeRecipe.getCategories().add(mexicanCategory);
//
        recipes.add(lemonadeRecipe);
        return recipes;

    }
}
