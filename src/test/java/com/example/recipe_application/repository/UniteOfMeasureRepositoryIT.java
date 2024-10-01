package com.example.recipe_application.repository;

import com.example.recipe_application.domain.UnitOfMeasure;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@DataJpaTest
class UniteOfMeasureRepositoryIT {

    @Autowired
    UniteOfMeasureRepository uniteOfMeasureRepository;

    @BeforeEach
    void setUp() {
    }

    @Test
    void findByDescription() throws Exception{
        Optional<UnitOfMeasure> uomOptional = uniteOfMeasureRepository.findByDescription("Teaspoon");
        assertEquals("Teaspoon", uomOptional.get().getDescription());
    }

    @Test
    void findByDescriptionCup() throws Exception{
        Optional<UnitOfMeasure> uomOptional = uniteOfMeasureRepository.findByDescription("Cup");
        assertEquals("Cup", uomOptional.get().getDescription());
    }
}