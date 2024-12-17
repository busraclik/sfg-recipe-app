package com.example.recipe_application.services;

import com.example.recipe_application.commands.UnitOfMeasureCommand;
import com.example.recipe_application.converters.UnitOfMeasureToUnitOfMeasureCommand;
import com.example.recipe_application.repository.UniteOfMeasureRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;


@Slf4j
@Service
public class UnitOfMeasureServiceImpl implements UnitOfMeasureService{

    private final UniteOfMeasureRepository uniteOfMeasureRepository;
    private final UnitOfMeasureToUnitOfMeasureCommand unitOfMeasureToUnitOfMeasureCommand;

    public UnitOfMeasureServiceImpl(UniteOfMeasureRepository uniteOfMeasureRepository, UnitOfMeasureToUnitOfMeasureCommand unitOfMeasureToUnitOfMeasureCommand) {
        this.uniteOfMeasureRepository = uniteOfMeasureRepository;
        this.unitOfMeasureToUnitOfMeasureCommand = unitOfMeasureToUnitOfMeasureCommand;
    }


    @Override
    public Set<UnitOfMeasureCommand> listAllUoms() {

        //mine
//        Set<UnitOfMeasureCommand> uomList = new HashSet<>();
//        uniteOfMeasureRepository.findAll().iterator()
//                .forEachRemaining(unitOfMeasure -> uomList.add(unitOfMeasureToUnitOfMeasureCommand.convert(unitOfMeasure)));
//
//        return uomList;

        return StreamSupport.stream(uniteOfMeasureRepository.findAll().spliterator(), false)
                .map(unitOfMeasureToUnitOfMeasureCommand::convert)
                .collect(Collectors.toSet());

    }
}
