package iftm.microservice.admin_service.domain;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import iftm.microservice.admin_service.utils.StatusTag;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(
    name = "ADMIN_TAGS",
    uniqueConstraints = {
        @UniqueConstraint(name = "UQ_ADMIN_TAGS_UUID", columnNames = {"TAG_UUID"}),
        @UniqueConstraint(name = "UQ_ADMIN_TAGS_NOME_CATEGORIA", columnNames = {"NOME", "CATEGORIA"})
    },
    indexes = {
        @Index(name = "IX_ADMIN_TAGS_STATUS_CATEGORIA", columnList = "STATUS, CATEGORIA")
    }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AdminTags {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "TAG_UUID", nullable = false, length = 36)
    private String tagUuid;

    @Column(name = "NOME", nullable = false, length = 120)
    private String name;

    @Column(name = "CATEGORIA", length = 100)
    private String categoria;

    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS", nullable = false, length = 15)
    private StatusTag status = StatusTag.ATIVA;

    @Column(name = "ORIGEM_ATUALIZADO_EM")
    private LocalDateTime origemAtualizadoEm;

    @CreationTimestamp
    @Column(name = "CRIADO_EM", nullable = false, updatable = false)
    private LocalDateTime criadoEm;

    @UpdateTimestamp
    @Column(name = "ATUALIZADO_EM", nullable = false)
    private LocalDateTime atualizadoEm;
    
}