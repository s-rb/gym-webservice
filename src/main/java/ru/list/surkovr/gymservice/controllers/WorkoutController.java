package ru.list.surkovr.gymservice.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.list.surkovr.gymservice.converters.DtoConverter;
import ru.list.surkovr.gymservice.domain.Workout;
import ru.list.surkovr.gymservice.dto.WorkoutDto;
import ru.list.surkovr.gymservice.services.interfaces.WorkoutService;

import java.time.LocalDate;
import java.util.List;

import static java.util.Objects.isNull;

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

    // Spring сам понимает, что нужно получить из БД объект с переданным в параметрах ИД нужного типа
    @GetMapping("{id}")
    public ResponseEntity<WorkoutDto> getById(@PathVariable("id") Workout workout) {
        return ResponseEntity.ok(dtoConverter.convert(workout));
    }

    @PostMapping
    public ResponseEntity<WorkoutDto> save(@RequestBody WorkoutDto workout) {
        Workout saved = workoutService.save(workout.getDate(), workout.getUserId(), workout.getSets());
        return ResponseEntity.ok(dtoConverter.convert(saved));
    }

    // TODO
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
    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        Workout workout = workoutService.findOne(id);
        if (isNull(workout)) return ResponseEntity.notFound().build();
        workoutService.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
