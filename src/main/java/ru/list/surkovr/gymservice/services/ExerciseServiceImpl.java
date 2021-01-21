package ru.list.surkovr.gymservice.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import ru.list.surkovr.gymservice.domain.Exercise;
import ru.list.surkovr.gymservice.repositories.ExerciseRepository;

import java.util.Collections;
import java.util.List;

/**
 * @author Roman Surkov
 * @created on 17.01.2021
 */
@Slf4j
@Service
public class ExerciseServiceImpl implements ExerciseService {

    @Autowired
    private final ExerciseRepository exerciseRepository;

    public ExerciseServiceImpl(ExerciseRepository exerciseRepository) {
        this.exerciseRepository = exerciseRepository;
    }

    @Override
    public List<Exercise> findAll() {
        return exerciseRepository.findAll();
    }

    @Override
    public List<Exercise> findAllByTag(String tagname) {
        if (StringUtils.hasText(tagname)) {
            return exerciseRepository.finaAllByTagName(tagname);
        } else {
            return Collections.emptyList();
        }
    }

    @Override
    public List<Exercise> findAllByTagNotAccurate(String tagname, int accuracy) {
        if (StringUtils.hasText(tagname)) {
            return exerciseRepository.finaAllByTagNameNotAccurate(tagname, accuracy);
        } else {
            return Collections.emptyList();
        }
    }
}
