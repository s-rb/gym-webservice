package ru.list.surkovr.gymservice.repositories;

import ru.list.surkovr.gymservice.domain.Workout;
import ru.list.surkovr.gymservice.domain.WorkoutSet;

import java.time.LocalDate;
import java.util.List;

public interface WorkoutRepository {
    List<Workout> findAllByDateBetweenAndUserId(LocalDate dateFrom, LocalDate dateTo, Long userId);

    Workout save(Workout workout);

    WorkoutSet save(WorkoutSet workoutSet);

    Workout findOne(Long id);

    void deleteById(Long id);
}
