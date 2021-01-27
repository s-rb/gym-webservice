package ru.list.surkovr.gymservice.services.interfaces;

import ru.list.surkovr.gymservice.domain.DocTemplate;
import ru.list.surkovr.gymservice.domain.DocTemplateCodeEnum;
import ru.list.surkovr.gymservice.domain.DocTemplateMimeType;

import java.io.InputStream;

/**
 * @author Roman Surkov
 * @created on 23.01.2021
 */
public interface FileStorageService {

    DocTemplate uploadFile(InputStream is, String name, Long fileSize, DocTemplateCodeEnum codeEnum, DocTemplateMimeType mimeType);
}
