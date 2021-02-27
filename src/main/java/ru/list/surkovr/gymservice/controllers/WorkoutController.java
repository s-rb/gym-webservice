package ru.list.surkovr.gymservice.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.list.surkovr.gymservice.converters.DtoConverter;
import ru.list.surkovr.gymservice.domain.Workout;
import ru.list.surkovr.gymservice.dto.WorkoutDto;
import ru.list.surkovr.gymservice.services.interfaces.WorkoutService;

import java.time.LocalDate;
import java.util.List;

/**
 * @author Roman Surkov
 * @created on 25.02.2021
 */
@Slf4j
@RestController
@RequestMapping("api/v1/workouts")
public class WorkoutController {

    @Autowired
    private final DtoConverter dtoConverter;
    @Autowired
    private final WorkoutService workoutService;

    public WorkoutController(DtoConverter dtoConverter, WorkoutService workoutService) {
        this.dtoConverter = dtoConverter;
        this.workoutService = workoutService;
    }

    @GetMapping
    public ResponseEntity<List<WorkoutDto>> getAll(@RequestParam(required = false, defaultValue = "null") LocalDate fromDate,
                                                   @RequestParam(required = false, defaultValue = "null") LocalDate toDate,
                                                   @RequestParam(required = false, defaultValue = "null") Long userId) {
        List<Workout> workouts = workoutService.findAll(userId, fromDate, toDate);
        return ResponseEntity.ok(dtoConverter.convertWorkOuts(workouts));
    }

//
//
//    @GetMapping("{id}")
//    public ResponseEntity<ExerciseDto> getById(@PathVariable("id") Exercise exerciseById) {
//        return ResponseEntity.ok(dtoConverter.convert(exerciseById));
//    }
//
//    @GetMapping("search")
//    public ResponseEntity<List<ExerciseDto>> searchByTag(@RequestParam String tag,
//                                                         @RequestParam(required = false) Boolean isNotAccurateSearch,
//                                                         @RequestParam(required = false, defaultValue = "30") Integer accuracy) {
//        List<Exercise> exercises;
//        if (isNull(isNotAccurateSearch) || !isNotAccurateSearch) {
//            exercises = exerciseService.findAllByTag(tag);
//        } else {
//            exercises = exerciseService.findAllByTagNotAccurate(tag, accuracy);
//        }
//        return ResponseEntity.ok(dtoConverter.convert(exercises));
//    }
//
//    @PostMapping
//    public ResponseEntity<ExerciseDto> save(@RequestBody ExerciseDto exercise) {
//        Exercise saved = exerciseService.save(exercise.getName(), exercise.getDescription(), exercise.getTags());
//        return ResponseEntity.ok(dtoConverter.convert(saved));
//    }
//
//    @PutMapping
//    public ResponseEntity<ExerciseDto> edit(@RequestBody ExerciseDto exerciseDto) {
//        Long id = exerciseDto.getId();
//        if (isNull(id)) {
//            return ResponseEntity.badRequest().header(HttpHeaders.WARNING, "Отсутствует идентификатор").build();
//        }
//        Exercise updated = exerciseService
//                .edit(id, exerciseDto.getName(), exerciseDto.getDescription(), exerciseDto.getTags());
//        if (nonNull(updated)) {
//            return ResponseEntity.ok(dtoConverter.convert(updated));
//        } else {
//            return ResponseEntity.badRequest()
//                    .header(HttpHeaders.WARNING, "Не найдено упражнение с переданным ID").build();
//        }
//    }
//
//    @DeleteMapping("{id}")
//    public ResponseEntity<Void> delete(@PathVariable Long id) {
//        Exercise exercise = exerciseService.findOne(id);
//        if (isNull(exercise)) return ResponseEntity.notFound().build();
//        exerciseService.deleteById(id);
//        return ResponseEntity.ok().build();
//    }
}
