package ru.list.surkovr.gymservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.list.surkovr.gymservice.domain.Exercise;

import java.util.List;

/**
 * @author Roman Surkov
 * @created on 17.01.2021
 */
public interface ExerciseRepository extends JpaRepository<Exercise, Long> {

    Exercise findExerciseByName(String name);

    @Query(nativeQuery = true, value =
            "SELECT DISTINCT e.* FROM exercises AS e " +
                    "INNER JOIN exercises_tags AS et ON et.exercise_id = e.exercise_id " +
                    "INNER JOIN tags AS t ON t.tag_id = et.tag_id " +
                    "WHERE t.tag_name ILIKE %?1%")
    List<Exercise> finaAllByTagName(String tagName);

    @Query(nativeQuery = true, value =
            "SELECT DISTINCT e.* FROM exercises AS e " +
                    "INNER JOIN exercises_tags AS et ON et.exercise_id = e.exercise_id " +
                    "INNER JOIN tags AS t ON t.tag_id = et.tag_id " +
                    "WHERE (lower(regexp_replace(t.tag_name, '[\\s]+', '', 'g')) % ?1)")
    List<Exercise> finaAllByTagNameNotAccurate(String tagname, int accuracy);
}
