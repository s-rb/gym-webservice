package ru.list.surkovr.gymservice.services.interfaces;

import ru.list.surkovr.gymservice.domain.Workout;
import ru.list.surkovr.gymservice.dto.WorkoutSetDto;

import java.time.LocalDate;
import java.util.List;

public interface WorkoutService {
    List<Workout> findAll(Long userId, LocalDate fromDate, LocalDate toDate);

    Workout save(LocalDate date, Long userId, List<WorkoutSetDto> sets);

    Workout findOne(Long id);

    void deleteById(Long id);

    Workout edit(Long id, LocalDate date, List<WorkoutSetDto> sets);
}
