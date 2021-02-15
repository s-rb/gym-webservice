package ru.list.surkovr.gymservice.converters;

import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import ru.list.surkovr.gymservice.domain.DocTemplate;
import ru.list.surkovr.gymservice.domain.Exercise;
import ru.list.surkovr.gymservice.domain.Tag;
import ru.list.surkovr.gymservice.domain.User;
import ru.list.surkovr.gymservice.dto.ExerciseDto;
import ru.list.surkovr.gymservice.dto.TagDto;
import ru.list.surkovr.gymservice.dto.UploadFileDto;
import ru.list.surkovr.gymservice.dto.UserDto;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;
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
        if (isNull(template)) return null;
        return UploadFileDto.builder()
                .id(template.getId()).name(template.getName())
                .created(template.getCreated()).updated(template.getUpdated()).build();
    }

    public List<TagDto> convertTags(List<Tag> tags) {
        if (CollectionUtils.isEmpty(tags)) {
            return Collections.emptyList();
        }
        return tags.stream().map(this::convert).filter(Objects::nonNull).collect(Collectors.toList());
    }

    public TagDto convert(Tag tag) {
        if (isNull(tag)) {
            return null;
        }
        return TagDto.builder()
                .id(tag.getId()).name(tag.getName())
                .build();
    }

    public List<UploadFileDto> convertTemplates(List<DocTemplate> templates) {
        if (CollectionUtils.isEmpty(templates)) return Collections.emptyList();
        return templates.stream().map(this::convert).filter(Objects::nonNull).collect(Collectors.toList());
    }

    public List<UserDto> convertUsers(List<User> users) {
        return users.stream().map(this::convert).collect(Collectors.toList());
    }

    public UserDto convert(User user) {
        return UserDto.builder()
                .id(user.getId()).username(user.getUsername())
                .lastName(user.getLastName()).firstName(user.getFirstName()).middleName(user.getMiddleName())
                .build();
    }
}
