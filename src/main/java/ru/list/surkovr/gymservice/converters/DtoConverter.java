package ru.list.surkovr.gymservice.converters;

import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import ru.list.surkovr.gymservice.domain.DocTemplate;
import ru.list.surkovr.gymservice.domain.Exercise;
import ru.list.surkovr.gymservice.domain.Tag;
import ru.list.surkovr.gymservice.dto.ExerciseDto;
import ru.list.surkovr.gymservice.dtos.UploadFileDto;

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

    public ExerciseDto convert(Exercise exercise) {
        ExerciseDto dto = new ExerciseDto();
        if (nonNull(exercise)) {
            dto.setId(exercise.getId());
            dto.setName(exercise.getName());
            dto.setDescription(exercise.getDescription());
            dto.setTags(
                    nonNull(exercise.getTags())
                            ? exercise.getTags().stream().map(Tag::getName).collect(Collectors.toSet())
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

    public UploadFileDto convert(DocTemplate template) {
        UploadFileDto dto = new UploadFileDto();
        if (nonNull(template)) {
            dto.setId(template.getId());
            dto.setName(template.getName());
            dto.setCreated(template.getCreated());
            dto.setUpdated(template.getUpdated());
        }
        return dto;
    }
}
