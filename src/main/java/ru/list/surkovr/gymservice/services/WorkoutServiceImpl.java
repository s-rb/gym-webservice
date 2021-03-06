package ru.list.surkovr.gymservice.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.list.surkovr.gymservice.domain.MeasureUnitEnum;
import ru.list.surkovr.gymservice.domain.Workout;
import ru.list.surkovr.gymservice.domain.WorkoutSet;
import ru.list.surkovr.gymservice.dto.WorkoutSetDto;
import ru.list.surkovr.gymservice.repositories.WorkoutRepository;
import ru.list.surkovr.gymservice.services.interfaces.ExerciseService;
import ru.list.surkovr.gymservice.services.interfaces.UserService;
import ru.list.surkovr.gymservice.services.interfaces.WorkoutService;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.util.List;
import java.util.Locale;

import static java.util.Objects.isNull;

@Slf4j
@Service
public class WorkoutServiceImpl implements WorkoutService {

    @Autowired
    private final WorkoutRepository workoutRepository;
    @Autowired
    private final UserService userService;
    @Autowired
    private final ExerciseService exerciseService;

    public WorkoutServiceImpl(WorkoutRepository workoutRepository,
                              UserService userService,
                              ExerciseService exerciseService) {
        this.workoutRepository = workoutRepository;
        this.userService = userService;
        this.exerciseService = exerciseService;
    }

    @Override
    public List<Workout> findAll(Long userId, LocalDate fromDate, LocalDate toDate) {
        return workoutRepository.findAllByDateBetweenAndUserId(fromDate, toDate, userId);
    }

    @Override
    public Workout save(LocalDate date, Long userId, List<WorkoutSetDto> sets) {
        Workout workout = Workout.builder()
                .date(date)
                .user(userService.findById(userId)).build();
        Workout savedWorkout = workoutRepository.save(workout);

        createSets(sets, savedWorkout);
        return savedWorkout;
    }

    private void createSets(List<WorkoutSetDto> sets, Workout savedWorkout) {
        sets.forEach(set -> {
            WorkoutSet workoutSet = WorkoutSet.builder()
                    .workout(savedWorkout)
                    .exercise(exerciseService.findOne(set.getExerciseId()))
                    .measureUnit(MeasureUnitEnum.valueOf(set.getMeasureUnit().toUpperCase(Locale.ROOT)))
                    .reps(set.getReps())
                    .value(set.getValue())
                    .build();
            WorkoutSet savedSet = workoutRepository.save(workoutSet);
            savedWorkout.getSets().add(savedSet);
        });
    }

    @Override
    public Workout findOne(Long id) {
        return workoutRepository.findOne(id);
    }

    @Override
    public void deleteById(Long id) {
        workoutRepository.deleteSetsByWorkoutId(id);
        workoutRepository.deleteById(id);
    }

    @Override
    public Workout edit(Long id, LocalDate date, List<WorkoutSetDto> sets) {
        Workout workout = workoutRepository.findOne(id);
        if (isNull(workout)) {
            throw new EntityNotFoundException("Workout by id: '" + id + "' not found");
        }
        workoutRepository.deleteSetsByWorkoutId(workout.getId());
        createSets(sets, workout);
        return workout;
    }
}
