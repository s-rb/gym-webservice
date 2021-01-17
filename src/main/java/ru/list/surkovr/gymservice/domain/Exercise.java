package ru.list.surkovr.gymservice.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

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
    @GeneratedValue(strategy = GenerationType.TABLE)
    @Column(name = "exercise_id")
    private Long id;

    @Column(name = "exercise_name")
    private String name;

    @Column(name = "exercise_description")
    private String description;

    @ManyToMany
    @JoinTable(name = "exercises_tags",
            joinColumns = {@JoinColumn(name = "exercise_id")},
            inverseJoinColumns = {@JoinColumn(name = "tag_id")})
    private Set<Tag> tags = new HashSet<>();
}
