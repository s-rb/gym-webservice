package ru.list.surkovr.gymservice.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.list.surkovr.gymservice.domain.Exercise;
import ru.list.surkovr.gymservice.repositories.ExerciseRepository;

import java.util.List;

/**
 * @author Roman Surkov
 * @created on 17.01.2021
 */
@Slf4j
@Service
public class ExerciseService {

    @Autowired
    private final ExerciseRepository exerciseRepository;

    public ExerciseService(ExerciseRepository exerciseRepository) {
        this.exerciseRepository = exerciseRepository;
    }

    public List<Exercise> findAll() {
        return exerciseRepository.findAll();
    }
}
