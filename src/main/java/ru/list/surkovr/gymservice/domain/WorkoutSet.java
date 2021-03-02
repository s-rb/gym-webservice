package ru.list.surkovr.gymservice.domain;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@Entity
@Table(name = "workout_sets")
public class WorkoutSet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "workout_set_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "exercise_id")
    private Exercise exercise;

    @Column(name = "measure_unit")
    @Enumerated(value = EnumType.STRING)
    private MeasureUnitEnum measureUnit;

    @Column(name = "set_value")
    private Integer value;

    @Column(name = "reps")
    private Integer reps;

    @ManyToOne
    @JoinColumn(name = "workout_id")
    private Workout workout;

    @Builder
    public WorkoutSet(Long id, Exercise exercise, MeasureUnitEnum measureUnit, Integer value, Integer reps,
                      Workout workout) {
        this.id = id;
        this.exercise = exercise;
        this.measureUnit = measureUnit;
        this.value = value;
        this.reps = reps;
        this.workout = workout;
    }
}
