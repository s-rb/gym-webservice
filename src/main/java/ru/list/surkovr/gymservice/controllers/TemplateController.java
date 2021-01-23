package ru.list.surkovr.gymservice.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import ru.list.surkovr.gymservice.converters.DtoConverter;
import ru.list.surkovr.gymservice.domain.DocTemplate;
import ru.list.surkovr.gymservice.domain.DocTemplateCodeEnum;
import ru.list.surkovr.gymservice.domain.DocTemplateMimeType;
import ru.list.surkovr.gymservice.dtos.UploadFileDto;
import ru.list.surkovr.gymservice.services.FileStorageService;

import java.io.IOException;

/**
 * @author Roman Surkov
 * @created on 23.01.2021
 */
@Slf4j
@Controller
@RequestMapping("api/v1/templates")
public class TemplateController {

    @Autowired
    private final FileStorageService fileStorageService;
    @Autowired
    private final DtoConverter dtoConverter;

    public TemplateController(FileStorageService fileStorageService, DtoConverter dtoConverter) {
        this.fileStorageService = fileStorageService;
        this.dtoConverter = dtoConverter;
    }

    // TODO сделать проверку на тип файлов (расширение)
    @PostMapping
    @ResponseBody
    public ResponseEntity<UploadFileDto> uploadFile(@RequestParam("file") MultipartFile file,
                                                    @RequestParam String name,
                                                    @RequestParam(required = false) DocTemplateCodeEnum code,
                                                    @RequestParam(required = false) DocTemplateMimeType mimeType) {
        try {
            DocTemplate saved = fileStorageService
                    .uploadFile(file.getInputStream(), name, file.getSize(), code, mimeType);
            return ResponseEntity.ok().body(dtoConverter.convert(saved));
        } catch (IOException e) {
            final String msg = "### In uploadFile get exception during uploading file";
            log.error(msg);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).header(HttpHeaders.WARNING, "msg").build();
        }
    }
}
