package ru.list.surkovr.gymservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.list.surkovr.gymservice.domain.DocTemplate;

/**
 * @author Roman Surkov
 * @created on 23.01.2021
 */
public interface DocTemplateRepository extends JpaRepository<DocTemplate, Long> {
}
