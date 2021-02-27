package ru.list.surkovr.gymservice.services.interfaces;

import ru.list.surkovr.gymservice.domain.Workout;

import java.time.LocalDate;
import java.util.List;

public interface WorkoutService {
    List<Workout> findAll(Long userId, LocalDate fromDate, LocalDate toDate);
}
