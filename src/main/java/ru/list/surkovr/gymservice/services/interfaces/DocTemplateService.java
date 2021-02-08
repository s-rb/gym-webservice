package ru.list.surkovr.gymservice.services.interfaces;

import ru.list.surkovr.gymservice.domain.DocTemplate;

import java.util.List;

/**
 * @author Roman Surkov
 * @created on 07.02.2021
 */
public interface DocTemplateService {
    List<DocTemplate> findAll();
}
