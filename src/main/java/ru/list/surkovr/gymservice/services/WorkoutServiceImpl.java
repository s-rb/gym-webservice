package ru.list.surkovr.gymservice.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.list.surkovr.gymservice.domain.Workout;
import ru.list.surkovr.gymservice.repositories.WorkoutRepository;
import ru.list.surkovr.gymservice.services.interfaces.WorkoutService;

import java.time.LocalDate;
import java.util.List;

import static java.util.Objects.nonNull;

@Slf4j
@Service
public class WorkoutServiceImpl implements WorkoutService {

    @Autowired
    private final WorkoutRepository workoutRepository;

    public WorkoutServiceImpl(WorkoutRepository workoutRepository) {
        this.workoutRepository = workoutRepository;
    }

    @Override
    public List<Workout> findAll(Long userId, LocalDate fromDate, LocalDate toDate) {
        if (nonNull(userId)) {



        }

        return workoutRepository.findAllByDateBetweenAndUserId(fromDate, toDate, userId);
    }
}
