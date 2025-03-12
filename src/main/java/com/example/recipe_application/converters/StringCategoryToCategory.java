package com.example.recipe_application.converters;

import com.example.recipe_application.domain.Category;
import com.example.recipe_application.repository.CategoryRepository;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Component
public class StringCategoryToCategory implements Converter<String, Category> {
    private final CategoryRepository categoryRepository;

    public StringCategoryToCategory(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }


    @Override
    public Category convert(String id) {

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


        int parsedId = Integer.parseInt(id);
        List<Category> selectedCategory = Arrays.asList(
               americanCategory, mexicanCategory);

        int index = parsedId -1;
        return selectedCategory.get(index);

    }
}
