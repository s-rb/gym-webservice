package ru.list.surkovr.gymservice.repositories;

import ru.list.surkovr.gymservice.domain.Workout;

import java.time.LocalDate;
import java.util.List;

public interface WorkoutRepository {
    List<Workout> findAllByDateBetweenAndUserId(LocalDate dateFrom, LocalDate dateTo, Long userId);
}
