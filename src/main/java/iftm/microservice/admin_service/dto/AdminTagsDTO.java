package iftm.microservice.admin_service.dto;

import iftm.microservice.admin_service.domain.AdminTags;
import iftm.microservice.admin_service.utils.StatusTag;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AdminTagsDTO {

    private String tagUuid;

    @NotBlank(message = "Campo obrigatório. Digite um nome.")
    private String nome;

    private String categoria;

    @NotNull(message = "Campo obrigatório. Informe um status.")
    private StatusTag status;

    public AdminTagsDTO(AdminTags adminTags) {

        this.tagUuid = adminTags.getTagUuid();
        this.nome = adminTags.getNome();
        this.categoria = adminTags.getCategoria();
        this.status = adminTags.getStatus();
        
    }

}