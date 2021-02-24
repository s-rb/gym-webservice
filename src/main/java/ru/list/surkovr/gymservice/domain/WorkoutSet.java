package ru.list.surkovr.gymservice.domain;

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
}
