package ru.list.surkovr.gymservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.list.surkovr.gymservice.domain.Exercise;

/**
 * @author Roman Surkov
 * @created on 17.01.2021
 */
public interface ExerciseRepository extends JpaRepository<Exercise, Long> {

    Exercise findExerciseByName(String name);
}
