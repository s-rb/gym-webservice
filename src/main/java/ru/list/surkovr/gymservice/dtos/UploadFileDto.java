package ru.list.surkovr.gymservice.dtos;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author Roman Surkov
 * @created on 23.01.2021
 */
@Data
public class UploadFileDto {

    private Long id;
    private String name;
    private LocalDateTime created;
    private LocalDateTime updated;
}
