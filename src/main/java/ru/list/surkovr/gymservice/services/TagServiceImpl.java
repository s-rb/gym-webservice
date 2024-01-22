package ru.list.surkovr.gymservice.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import ru.list.surkovr.gymservice.domain.Tag;
import ru.list.surkovr.gymservice.repositories.TagRepository;
import ru.list.surkovr.gymservice.services.interfaces.TagService;

import java.util.List;
import java.util.NoSuchElementException;
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
    public Set<Tag> findAllBy(List<String> tagNames) {
        return tagRepository.findAllTagsByNameIsIn(tagNames);
    }

    @Override
    public Set<Tag> createTags(List<String> tagsToCreate) {
        Set<Tag> foundTags = findAllBy(tagsToCreate);
        if (!CollectionUtils.isEmpty(foundTags)) {
            tagsToCreate.removeIf(t -> foundTags.stream().map(Tag::getName)
                    .anyMatch(tagName -> tagName.equals(t)));
        }
        Set<Tag> createdTags = tagsToCreate
                .stream().map(this::createTag).filter(Objects::nonNull).collect(Collectors.toSet());
        return createdTags;
    }

    @Override
    public List<Tag> findAll() {
        return tagRepository.findAll();
    }

    @Override
    public Tag save(String name) {
        if (StringUtils.hasText(name)) {
            final Tag tag = Tag.builder().name(name.toLowerCase()).build();
            return tagRepository.save(tag);
        }
        throw new IllegalArgumentException("Tag name could not be empty or blank");
    }

    @Override
    public Tag edit(Long id, String name) {
        if (StringUtils.hasText(name)) {
            Tag tagById = tagRepository.findById(id)
                    .orElseThrow(() -> new NoSuchElementException("Tag not found by id: '" + id + "'"));
            tagById.setName(name);
            return tagRepository.save(tagById);
        }
        throw new IllegalArgumentException("Tag name could not be empty or blank");
    }

    @Override
    public Tag findOne(Long id) {
        return tagRepository.findById(id).orElse(null);
    }

    @Override
    public void deleteById(Long id) {
        tagRepository.deleteExerciseTagsByTagId(id);
        tagRepository.deleteById(id);
    }

    public Tag createTag(String name) {
        Tag tag = new Tag();
        tag.setName(name);
        Tag saved = tagRepository.save(tag);
        return nonNull(saved.getId()) ? saved : null;
    }
}
