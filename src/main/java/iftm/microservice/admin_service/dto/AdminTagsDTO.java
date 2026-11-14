package iftm.microservice.admin_service.dto;

import iftm.microservice.admin_service.domain.AdminTags; 
import iftm.microservice.admin_service.utils.StatusTag;  
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class AdminTagsDTO {

    private String tagUuid;

    @NotBlank(message = "Campo obrigatório. Digite um nome.")
    private String nome;

    private String categoria;

    @NotNull(message = "Campo obrigatório. Informe um status.")
    private StatusTag status;

    public AdminTagsDTO() {
    }

    public AdminTagsDTO(AdminTags adminTags) {
        this.tagUuid = adminTags.getTag_uuid(); 
        this.nome = adminTags.getName();        
        this.categoria = adminTags.getCategoria();
        this.status = adminTags.getStatus();
    }

    public String getTagUuid() {
        return tagUuid;
    }

    public void setTagUuid(String tagUuid) {
        this.tagUuid = tagUuid;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
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
}