package ru.list.surkovr.gymservice.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import ru.list.surkovr.gymservice.domain.Tag;
import ru.list.surkovr.gymservice.repositories.TagRepository;
import ru.list.surkovr.gymservice.services.interfaces.TagService;

import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.Objects.nonNull;

@Service
public class TagServiceImpl implements TagService {

    @Autowired
    private final TagRepository tagRepository;

    public TagServiceImpl(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    @Override
    public Set<Tag> findAllBy(Set<String> tagNames) {
        return tagRepository.findAllTagsByNameIsIn(tagNames);
    }

    @Override
    public Set<Tag> createTags(Set<String> tagsToCreate) {
        Set<Tag> foundTags = findAllBy(tagsToCreate);
        if (!CollectionUtils.isEmpty(foundTags)) {
            tagsToCreate.removeIf(t -> foundTags.stream().map(Tag::getName)
                    .anyMatch(tagName -> tagName.equals(t)));
        }
        Set<Tag> createdTags = tagsToCreate
                .stream().map(this::createTag).filter(Objects::nonNull).collect(Collectors.toSet());
        return createdTags;
    }

    public Tag createTag(String name) {
        Tag tag = new Tag();
        tag.setName(name);
        Tag saved = tagRepository.save(tag);
        return nonNull(saved.getId()) ? saved : null;
    }
}
