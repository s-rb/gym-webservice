package ru.list.surkovr.gymservice.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.util.CollectionUtils;
import ru.list.surkovr.gymservice.utils.DescriptionAnnotation;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "exercise_id")
    @DescriptionAnnotation("Идентификтор")
    private Long id;

    @Column(name = "exercise_name")
    @DescriptionAnnotation("Наименование")
    private String name;

    @Column(name = "exercise_description")
    @DescriptionAnnotation("Описание")
    private String description;

    @ManyToMany
    @JoinTable(name = "exercises_tags",
            joinColumns = {@JoinColumn(name = "exercise_id")},
            inverseJoinColumns = {@JoinColumn(name = "tag_id")})
    @DescriptionAnnotation("Тэги")
    private Set<Tag> tags = new HashSet<>();

    public static final String TAG_DELIMITER = "|";

    public String getExportString(String delimiter) {
        String tagStr = CollectionUtils.isEmpty(tags) ? ""
                : tags.stream().map(Tag::getName).collect(Collectors.joining(TAG_DELIMITER));
        return id
                + delimiter + name
                + delimiter + description
                + delimiter + tagStr;
    }
}
