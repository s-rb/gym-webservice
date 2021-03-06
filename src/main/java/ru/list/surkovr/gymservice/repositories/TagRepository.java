package ru.list.surkovr.gymservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.list.surkovr.gymservice.domain.Tag;

import java.util.List;
import java.util.Set;

public interface TagRepository extends JpaRepository<Tag, Long> {

    Set<Tag> findAllTagsByNameIsIn(List<String> tagNames);

    @Query(value = "DELETE FROM exercises_tags et WHERE et.tag_id = :id", nativeQuery = true)
    void deleteExerciseTagsByTagId(Long id);
}
