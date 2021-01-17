package ru.list.surkovr.gymservice.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.list.surkovr.gymservice.converters.DtoConverter;
import ru.list.surkovr.gymservice.domain.Exercise;
import ru.list.surkovr.gymservice.dto.ExerciseDto;
import ru.list.surkovr.gymservice.services.ExerciseService;

import java.util.List;

/**
 * @author Roman Surkov
 * @created on 17.01.2021
 */
@Slf4j
@RestController
@RequestMapping("api/v1/exercises")
public class ExerciseController {

    @Autowired
    private final DtoConverter dtoConverter;
    @Autowired
    private final ExerciseService exerciseService;

    public ExerciseController(DtoConverter dtoConverter, ExerciseService exerciseService) {
        this.dtoConverter = dtoConverter;
        this.exerciseService = exerciseService;
    }

    @GetMapping
    public ResponseEntity<List<ExerciseDto>> getAll() {
        List<Exercise> exercises = exerciseService.findAll();
        return ResponseEntity.ok(dtoConverter.convert(exercises));
    }

    @GetMapping("{id}")
    public ResponseEntity<ExerciseDto> getById(@PathVariable("id") Exercise exerciseById) {
        return ResponseEntity.ok(dtoConverter.convert(exerciseById));
    }
}
