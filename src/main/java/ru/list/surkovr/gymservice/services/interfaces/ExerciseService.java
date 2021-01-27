package ru.list.surkovr.gymservice.services.interfaces;

import ru.list.surkovr.gymservice.domain.Exercise;

import java.util.List;
import java.util.Set;

/**
 * @author Roman Surkov
 * @created on 21.01.2021
 */
public interface ExerciseService {

    List<Exercise> findAll();

    List<Exercise> findAllByTag(String tagname);

    List<Exercise> findAllByTagNotAccurate(String tagname, int accuracy);

    Exercise save(String name, String description, Set<String> tags);

    Exercise edit(Long exerciseId, String name, String description, Set<String> tags);

    Exercise findOne(Long id);

    void deleteById(Long id);
}
