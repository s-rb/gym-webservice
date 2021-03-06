package ru.list.surkovr.gymservice.services.interfaces;

import ru.list.surkovr.gymservice.domain.Tag;

import java.util.List;
import java.util.Set;

public interface TagService {

    Set<Tag> findAllBy(List<String> tagNames);

    Set<Tag> createTags(List<String> tagsToCreate);

    List<Tag> findAll();

    Tag save(String name);

    Tag edit(Long id, String name);

    Tag findOne(Long id);

    void deleteById(Long id);
}
