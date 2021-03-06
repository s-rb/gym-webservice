package ru.list.surkovr.gymservice.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.list.surkovr.gymservice.converters.DtoConverter;
import ru.list.surkovr.gymservice.domain.Tag;
import ru.list.surkovr.gymservice.dto.TagDto;
import ru.list.surkovr.gymservice.services.interfaces.TagService;

import java.util.List;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

/**
 * @author Roman Surkov
 * @created on 07.02.2021
 */
@Slf4j
@RestController
@RequestMapping("api/v1/tags")
public class TagController {

    @Autowired
    private final TagService tagService;
    @Autowired
    private final DtoConverter dtoConverter;

    public TagController(TagService tagService, DtoConverter dtoConverter) {
        this.tagService = tagService;
        this.dtoConverter = dtoConverter;
    }

    // TODO добавление тегов только в нижнем регистре и соответствующая проверка (чтобы не было "Спина" и "спина")
    @GetMapping
    public ResponseEntity<List<TagDto>> getAll() {
        List<Tag> tags = tagService.findAll();
        return ResponseEntity.ok(dtoConverter.convertTags(tags));
    }

    @GetMapping("{id}")
    public ResponseEntity<TagDto> getById(@PathVariable("id") Tag tagById) {
        return ResponseEntity.ok(dtoConverter.convert(tagById));
    }

    @PostMapping
    public ResponseEntity<TagDto> save(@RequestBody TagDto tag) {
        Tag saved = tagService.save(tag.getName());
        return ResponseEntity.ok(dtoConverter.convert(saved));
    }

    @PutMapping
    public ResponseEntity<TagDto> edit(@RequestBody TagDto tagDto) {
        Long id = tagDto.getId();
        if (isNull(id)) {
            return ResponseEntity.badRequest().header(HttpHeaders.WARNING, "Отсутствует идентификатор").build();
        }
        Tag updated = tagService.edit(id, tagDto.getName());
        if (nonNull(updated)) {
            return ResponseEntity.ok(dtoConverter.convert(updated));
        } else {
            return ResponseEntity.badRequest()
                    .header(HttpHeaders.WARNING, "Не найден тэг с переданным ID").build();
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        Tag tag = tagService.findOne(id);
        if (isNull(tag)) return ResponseEntity.notFound().build();
        tagService.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
