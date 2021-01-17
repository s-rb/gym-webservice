package ru.list.surkovr.gymservice.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author Roman Surkov
 * @created on 17.01.2021
 */

@Data
@Entity
@Table(name = "exercises")
@NoArgsConstructor
@AllArgsConstructor
public class Exercise {

    @Id
    @Column(name = "exercise_id")
    private Long id;

    @Column(name = "exercise_name")
    private String name;
}
