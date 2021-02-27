package ru.list.surkovr.gymservice.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
public class WorkoutDto {

    private Long id;
    private LocalDate date;
    private Long userId;
    private List<WorkoutSetDto> sets;

    @Builder
    public WorkoutDto(Long id, LocalDate date, Long userId, List<WorkoutSetDto> sets) {
        this.id = id;
        this.date = date;
        this.userId = userId;
        this.sets = sets;
    }
}
