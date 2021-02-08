package ru.list.surkovr.gymservice.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.list.surkovr.gymservice.domain.DocTemplate;
import ru.list.surkovr.gymservice.repositories.DocTemplateRepository;
import ru.list.surkovr.gymservice.services.interfaces.DocTemplateService;

import java.util.List;

/**
 * @author Roman Surkov
 * @created on 07.02.2021
 */
@Slf4j
@Service
public class DocTemplateServiceImpl implements DocTemplateService {

    @Autowired
    private final DocTemplateRepository docTemplateRepository;

    public DocTemplateServiceImpl(DocTemplateRepository docTemplateRepository) {
        this.docTemplateRepository = docTemplateRepository;
    }

    @Override
    public List<DocTemplate> findAll() {
        return docTemplateRepository.findAll();
    }
}
