package com.example.recipe_application.services;


import com.example.recipe_application.commands.UnitOfMeasureCommand;
import com.example.recipe_application.converters.UnitOfMeasureToUnitOfMeasureCommand;
import com.example.recipe_application.domain.UnitOfMeasure;
import com.example.recipe_application.repository.UniteOfMeasureRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class UnitOfMeasureImplTest {

    UnitOfMeasureService unitOfMeasureService;
    UnitOfMeasureToUnitOfMeasureCommand unitOfMeasureToUnitOfMeasureCommand = new UnitOfMeasureToUnitOfMeasureCommand();

    @Mock
    UniteOfMeasureRepository uniteOfMeasureRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        unitOfMeasureService = new UnitOfMeasureServiceImpl(uniteOfMeasureRepository,unitOfMeasureToUnitOfMeasureCommand);
    }

    @Test
    void listAllUomsTest() throws Exception{
        Set<UnitOfMeasure> unitOfMeasureList = new HashSet<>();
        UnitOfMeasure unitOfMeasure = new UnitOfMeasure();
        unitOfMeasure.setId(1L);
        unitOfMeasureList.add(unitOfMeasure);

        UnitOfMeasure unitOfMeasure1 = new UnitOfMeasure();
        unitOfMeasure1.setId(2L);
        unitOfMeasureList.add(unitOfMeasure1);

        when(uniteOfMeasureRepository.findAll()).thenReturn(unitOfMeasureList);

        Set<UnitOfMeasureCommand> unitOfMeasureCommands = unitOfMeasureService.listAllUoms();

        assertEquals(2, unitOfMeasureCommands.size());
        verify(uniteOfMeasureRepository, times(1)).findAll();

    }


}
