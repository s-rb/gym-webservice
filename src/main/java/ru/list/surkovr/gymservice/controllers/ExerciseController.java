package ru.list.surkovr.gymservice.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.list.surkovr.gymservice.converters.DtoConverter;
import ru.list.surkovr.gymservice.domain.Exercise;
import ru.list.surkovr.gymservice.dto.ExerciseDto;
import ru.list.surkovr.gymservice.services.interfaces.ExerciseService;

import java.util.List;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

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

    @PostMapping
    public ResponseEntity<ExerciseDto> save(@RequestBody ExerciseDto exercise) {
        Exercise saved = exerciseService.save(exercise.getName(), exercise.getDescription(), exercise.getTags());
        return ResponseEntity.ok(dtoConverter.convert(saved));
    }

    @PutMapping
    public ResponseEntity<ExerciseDto> edit(@RequestBody ExerciseDto exerciseDto) {
        Long id = exerciseDto.getId();
        if (isNull(id)) {
            return ResponseEntity.badRequest().header(HttpHeaders.WARNING, "Отсутствует идентификатор").build();
        }
        Exercise updated = exerciseService
                .edit(id, exerciseDto.getName(), exerciseDto.getDescription(), exerciseDto.getTags());
        if (nonNull(updated)) {
            return ResponseEntity.ok(dtoConverter.convert(updated));
        } else {
            return ResponseEntity.badRequest()
                    .header(HttpHeaders.WARNING, "Не найдено упражнение с переданным ID").build();
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        Exercise exercise = exerciseService.findOne(id);
        if (isNull(exercise)) return ResponseEntity.notFound().build();
        exerciseService.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
