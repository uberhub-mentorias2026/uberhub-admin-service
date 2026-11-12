package iftm.microservice.admin_service.domain;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import iftm.microservice.admin_service.utils.StatusTag;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

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
public class AdminTags {
    
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "TAG_UUID", nullable = false, length = 36)
    private String tag_uuid;

    @Column(name = "NOME", nullable = false, length = 120)
    private String name;

    @Column(name = "CATEGORIA", nullable = false, length = 100)
    private String categoria;

    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS", nullable = false, length = 15)
    private StatusTag status; 

    @Column(name = "ORIGEM_ATUALIZADO_EM")
    private LocalDateTime origemAtualizadoEm;

    @CreationTimestamp 
    @Column(name = "CRIADO_EM", nullable = false, updatable = false)
    private LocalDateTime criadoEm;

    @UpdateTimestamp
    @Column(name = "ATUALIZADO_EM", nullable = false)
    private LocalDateTime atualizadoEm;

    public AdminTags() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTag_uuid() {
        return tag_uuid;
    }

    public void setTag_uuid(String tag_uuid) {
        this.tag_uuid = tag_uuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public StatusTag getStatus() {
        return status;
    }

    public void setStatus(StatusTag status) {
        this.status = status;
    }

    public LocalDateTime getOrigemAtualizadoEm() {
        return origemAtualizadoEm;
    }

    public void setOrigemAtualizadoEm(LocalDateTime origemAtualizadoEm) {
        this.origemAtualizadoEm = origemAtualizadoEm;
    }

    public LocalDateTime getCriadoEm() {
        return criadoEm;
    }

    public void setCriadoEm(LocalDateTime criadoEm) {
        this.criadoEm = criadoEm;
    }

    public LocalDateTime getAtualizadoEm() {
        return atualizadoEm;
    }

    public void setAtualizadoEm(LocalDateTime atualizadoEm) {
        this.atualizadoEm = atualizadoEm;
    }
}