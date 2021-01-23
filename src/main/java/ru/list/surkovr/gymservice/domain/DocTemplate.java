package ru.list.surkovr.gymservice.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * @author Roman Surkov
 * @created on 23.01.2021
 */
@Data
@Entity
@NoArgsConstructor
@Table(name = "doc_templates")
public class DocTemplate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "doc_template_id")
    private Long id;

    @Column(name = "doc_template_name")
    private String name;

    @CreationTimestamp
    @Column(name = "doc_template_created")
    private LocalDateTime created;

    @UpdateTimestamp
    @Column(name = "doc_template_updated")
    private LocalDateTime updated;

    @Column(name = "doc_template_data")
    private byte[] data;

    @Column(name = "doc_template_mime_type")
    @Enumerated(value = EnumType.STRING)
    private DocTemplateMimeType mimeType;

    @Column(name = "doc_template_code")
    @Enumerated(value = EnumType.STRING)
    private DocTemplateCodeEnum code;
}
