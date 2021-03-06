package ru.list.surkovr.gymservice.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.list.surkovr.gymservice.converters.DtoConverter;
import ru.list.surkovr.gymservice.domain.DocTemplate;
import ru.list.surkovr.gymservice.domain.DocTemplateCodeEnum;
import ru.list.surkovr.gymservice.domain.DocTemplateMimeType;
import ru.list.surkovr.gymservice.dto.UploadFileDto;
import ru.list.surkovr.gymservice.services.interfaces.DocTemplateService;
import ru.list.surkovr.gymservice.services.interfaces.FileStorageService;
import ru.list.surkovr.gymservice.utils.Validator;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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
    @Autowired
    private final DocTemplateService docTemplateService;

    public TemplateController(FileStorageService fileStorageService, DtoConverter dtoConverter,
                              DocTemplateService docTemplateService) {
        this.fileStorageService = fileStorageService;
        this.dtoConverter = dtoConverter;
        this.docTemplateService = docTemplateService;
    }

    @PostMapping
    @ResponseBody
    public ResponseEntity<UploadFileDto> uploadFile(@RequestParam("file") MultipartFile file,
                                                    @RequestParam String name,
                                                    @RequestParam(required = false) DocTemplateCodeEnum code,
                                                    @RequestParam(required = false) DocTemplateMimeType mimeType) {
        try {
            String originalFilename = file.getOriginalFilename();
            List<String> allowedExtensions = Arrays.stream(DocTemplateMimeType.values()).map(Enum::name).collect(Collectors.toList());
            boolean isFileExtensionValid = Validator.isFileExtensionValid(originalFilename,
                    allowedExtensions);
            if (!isFileExtensionValid) {
                String msg = "File extension is not valid. Supported extensions: "
                        + String.join(", ", allowedExtensions);
                log.error("### In uploadFile caught exception. " + msg);
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).header(HttpHeaders.WARNING, msg).build();
            }

            DocTemplate saved = fileStorageService
                    .uploadFile(file.getInputStream(), name, file.getSize(), code, mimeType);
            return ResponseEntity.ok().body(dtoConverter.convert(saved));
        } catch (IOException e) {
            final String msg = "### In uploadFile get exception during uploading file";
            log.error(msg);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).header(HttpHeaders.WARNING, "msg").build();
        }
    }
    // TODO при экспорте и отсутствии шаблона - выдавать соответствующее сообщение, что нужен шаблон

    @GetMapping
    @ResponseBody
    public ResponseEntity<List<UploadFileDto>> getAllUploadedFilesData() {
        List<DocTemplate> templates = docTemplateService.findAll();
        return ResponseEntity.ok().body(dtoConverter.convertTemplates(templates));
    }
}
