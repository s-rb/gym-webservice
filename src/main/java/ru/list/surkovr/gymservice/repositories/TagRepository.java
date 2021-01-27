package ru.list.surkovr.gymservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.list.surkovr.gymservice.domain.Tag;

import java.util.Set;

public interface TagRepository extends JpaRepository<Tag, Long> {

    Set<Tag> findAllTagsByNameIsIn(Set<String> tagNames);
}
