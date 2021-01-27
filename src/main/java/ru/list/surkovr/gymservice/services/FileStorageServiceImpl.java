package ru.list.surkovr.gymservice.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.list.surkovr.gymservice.domain.DocTemplate;
import ru.list.surkovr.gymservice.domain.DocTemplateCodeEnum;
import ru.list.surkovr.gymservice.domain.DocTemplateMimeType;
import ru.list.surkovr.gymservice.repositories.DocTemplateRepository;
import ru.list.surkovr.gymservice.services.interfaces.FileStorageService;

import java.io.IOException;
import java.io.InputStream;

/**
 * @created on 23.01.2021
 * @author Roman Surkov
 */
@Slf4j
@Service
public class FileStorageServiceImpl implements FileStorageService {

    @Autowired
    private final DocTemplateRepository docTemplateRepository;

    public FileStorageServiceImpl(DocTemplateRepository docTemplateRepository) {
        this.docTemplateRepository = docTemplateRepository;
    }

    @Override
    public DocTemplate uploadFile(InputStream is, String name, Long fileSize, DocTemplateCodeEnum codeEnum,
                           DocTemplateMimeType mimeType) {
        DocTemplate template = new DocTemplate();
        template.setName(name);
        template.setMimeType(mimeType);
        template.setCode(codeEnum);
        try {
            template.setData(is.readAllBytes());
            return docTemplateRepository.save(template);
        } catch (IOException e) {
            log.error("### In uploadFile get exception when uploading file");
            return null;
        }
    }
}
