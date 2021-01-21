package ru.list.surkovr.gymservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

/**
 * @author Roman Surkov
 * @created on 17.01.2021
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExerciseDto {

    private Long id;
    private String name;
    private Set<String> tags;
}
