package ru.list.surkovr.gymservice.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import ru.list.surkovr.gymservice.domain.Exercise;
import ru.list.surkovr.gymservice.domain.Tag;
import ru.list.surkovr.gymservice.repositories.ExerciseRepository;
import ru.list.surkovr.gymservice.services.interfaces.ExerciseService;
import ru.list.surkovr.gymservice.services.interfaces.TagService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

/**
 * @author Roman Surkov
 * @created on 17.01.2021
 */
@Slf4j
@Service
public class ExerciseServiceImpl implements ExerciseService {

    @Autowired
    private final ExerciseRepository exerciseRepository;
    @Autowired
    private final TagService tagService;

    public ExerciseServiceImpl(ExerciseRepository exerciseRepository, TagService tagService) {
        this.exerciseRepository = exerciseRepository;
        this.tagService = tagService;
    }

    @Override
    public List<Exercise> findAll() {
        return exerciseRepository.findAll();
    }

    @Override
    public List<Exercise> findAllByTag(String tagname) {
        if (StringUtils.hasText(tagname)) {
            return exerciseRepository.finaAllByTagName(tagname);
        } else {
            return Collections.emptyList();
        }
    }

    @Override
    public List<Exercise> findAllByTagNotAccurate(String tagname, int accuracy) {
        if (StringUtils.hasText(tagname)) {
            return exerciseRepository.finaAllByTagNameNotAccurate(tagname.toLowerCase(), accuracy);
        } else {
            return Collections.emptyList();
        }
    }

    @Override
    public Exercise save(String name, String description, List<String> tags) {
        Exercise exercise = new Exercise();
        exercise.setName(name);
        exercise.setDescription(description);
        setTags(tags, exercise);
        return exerciseRepository.save(exercise);
    }

    private void setTags(List<String> tags, Exercise exercise) {
        if (!CollectionUtils.isEmpty(tags)) {
            Set<Tag> foundTags = tagService.findAllBy(tags);
            List<String> tagsToCreate = new ArrayList<>();
            tags.forEach(tagName -> {
                if (foundTags.stream().map(Tag::getName).noneMatch(t -> t.equalsIgnoreCase(tagName))) {
                    tagsToCreate.add(tagName);
                }
            });
            Set<Tag> createdTags = tagService.createTags(tagsToCreate.stream().distinct().collect(Collectors.toList()));
            foundTags.addAll(createdTags);
            exercise.setTags(foundTags);
        }
    }

    @Override
    public Exercise edit(Long exerciseId, String name, String description, List<String> tags) {
        Exercise exercise = exerciseRepository.findById(exerciseId).orElse(null);
        if (isNull(exercise)) return null;
        exercise.setName(name);
        exercise.setDescription(description);
        setTags(tags, exercise);
        return exerciseRepository.save(exercise);

    }

    @Override
    public Exercise findOne(Long id) {
        return exerciseRepository.findById(id).orElse(null);
    }

    @Override
    public void deleteById(Long id) {
        if (nonNull(id)) {
            exerciseRepository.deleteById(id);
        }
    }
}
