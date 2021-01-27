package ru.list.surkovr.gymservice.services.interfaces;

import ru.list.surkovr.gymservice.domain.Tag;

import java.util.Set;

public interface TagService {
    Set<Tag> findAllBy(Set<String> tagNames);

    Set<Tag> createTags(Set<String> tagsToCreate);
}
