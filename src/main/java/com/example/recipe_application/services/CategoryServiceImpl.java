package com.example.recipe_application.services;

import com.example.recipe_application.domain.Category;
import com.example.recipe_application.repository.CategoryRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class CategoryServiceImpl implements CategoryService{
    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<Category> findAll() {
        List<Category> categories = (List<Category>) categoryRepository.findAll();
        return categories;
    }
}
