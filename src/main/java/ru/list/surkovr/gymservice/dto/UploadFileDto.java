package ru.list.surkovr.gymservice.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @author Roman Surkov
 * @created on 23.01.2021
 */
@Data
@NoArgsConstructor
@Builder
public class UploadFileDto {

    private Long id;
    private String name;
    private LocalDateTime created;
    private LocalDateTime updated;
}
