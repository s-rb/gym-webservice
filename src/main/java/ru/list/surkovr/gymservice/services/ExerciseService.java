package ru.list.surkovr.gymservice.services;

import ru.list.surkovr.gymservice.domain.Exercise;

import java.util.List;

/**
 * @author Roman Surkov
 * @created on 21.01.2021
 */
public interface ExerciseService {

    List<Exercise> findAll();

    List<Exercise> findAllByTag(String tagname);

    List<Exercise> findAllByTagNotAccurate(String tagname, int accuracy);
}
