package ru.list.surkovr.gymservice.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class WorkoutSetDto {

    private Long id;
    private Long exerciseId;
    private String exerciseName;
    private String measureUnit;
    private Integer value;
    private Integer reps;

    @Builder
    public WorkoutSetDto(Long id, Long exerciseId, String exerciseName, String measureUnit, Integer value, Integer reps) {
        this.id = id;
        this.exerciseId = exerciseId;
        this.exerciseName = exerciseName;
        this.measureUnit = measureUnit;
        this.value = value;
        this.reps = reps;
    }
}
