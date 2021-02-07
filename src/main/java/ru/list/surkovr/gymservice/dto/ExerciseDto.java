package ru.list.surkovr.gymservice.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

/**
 * @author Roman Surkov
 * @created on 17.01.2021
 */
@Data
@NoArgsConstructor
@Builder
public class ExerciseDto {

    private Long id;
    private String name;
    private String description;
    private Set<String> tags;
}
