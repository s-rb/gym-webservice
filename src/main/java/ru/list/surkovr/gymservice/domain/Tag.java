package ru.list.surkovr.gymservice.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * @author Roman Surkov
 * @created on 17.01.2021
 */
@Data
@NoArgsConstructor
@Entity
@Table(name = "tags")
@Builder
public class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tag_id")
    private Long id;

    @Column(name = "tag_name", unique = true)
    private String name;
}
