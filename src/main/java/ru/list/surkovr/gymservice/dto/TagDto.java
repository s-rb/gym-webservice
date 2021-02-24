package ru.list.surkovr.gymservice.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Roman Surkov
 * @created on 07.02.2021
 */
@Data
@NoArgsConstructor
public class TagDto {

    private Long id;
    private String name;

    @Builder
    public TagDto(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
