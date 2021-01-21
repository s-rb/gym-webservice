package ru.list.surkovr.gymservice.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.list.surkovr.gymservice.converters.DtoConverter;
import ru.list.surkovr.gymservice.domain.Exercise;
import ru.list.surkovr.gymservice.dto.ExerciseDto;
import ru.list.surkovr.gymservice.services.ExerciseService;

import java.util.List;

import static java.util.Objects.isNull;

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

    @GetMapping("search")
    public ResponseEntity<List<ExerciseDto>> searchByTag(@RequestParam String tag,
                                                         @RequestParam(required = false) Boolean isNotAccurateSearch,
                                                         @RequestParam(required = false, defaultValue = "30") Integer accuracy) {
        List<Exercise> exercises;
        if (isNull(isNotAccurateSearch) || !isNotAccurateSearch) {
            exercises = exerciseService.findAllByTag(tag);
        } else {
            exercises = exerciseService.findAllByTagNotAccurate(tag, accuracy);
        }
        return ResponseEntity.ok(dtoConverter.convert(exercises));
    }
}
