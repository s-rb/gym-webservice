package ru.list.surkovr.gymservice.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author Roman Surkov
 * @created on 17.01.2021
 */
@Data
@NoArgsConstructor
public class ExerciseDto {

    private Long id;
    private String name;
    private String description;
    private List<String> tags;

    @Builder
    public ExerciseDto(Long id, String name, String description, List<String> tags) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.tags = tags;
    }
}
