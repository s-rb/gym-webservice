package ru.list.surkovr.gymservice.converters;

import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import ru.list.surkovr.gymservice.domain.Exercise;
import ru.list.surkovr.gymservice.domain.Tag;
import ru.list.surkovr.gymservice.dto.ExerciseDto;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Objects.nonNull;

/**
 * @author Roman Surkov
 * @created on 17.01.2021
 */
@Component
public class DtoConverter {

    public ExerciseDto convert(Exercise exerciseById) {
        ExerciseDto dto = new ExerciseDto();
        if (nonNull(exerciseById)) {
            dto.setId(exerciseById.getId());
            dto.setName(exerciseById.getName());
            dto.setTags(
                    nonNull(exerciseById.getTags())
                    ? exerciseById.getTags().stream().map(Tag::getName).collect(Collectors.toSet())
                    : Collections.emptySet());
        }
        return dto;
    }

    public List<ExerciseDto> convert(List<Exercise> exercises) {
        if (CollectionUtils.isEmpty(exercises)) {
            return Collections.emptyList();
        }
        return exercises.stream().map(this::convert).collect(Collectors.toList());
    }
}
