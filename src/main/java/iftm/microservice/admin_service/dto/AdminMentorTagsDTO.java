package iftm.microservice.admin_service.dto;

import iftm.microservice.admin_service.domain.AdminMentorTags;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AdminMentorTagsDTO {

    @NotNull(message = "O ID do mentor é obrigatório.")
    private Long mentorId;

    @NotNull(message = "O ID da tag é obrigatório.")
    private Long tagId;

    private String mentor;
    private String tag;

    public AdminMentorTagsDTO(AdminMentorTags adminMentorTags) {
        this.mentorId = adminMentorTags.getMentor().getId();
        this.tagId = adminMentorTags.getTag().getId();

        this.mentor = adminMentorTags.getMentor().getUsuario() != null
            ? adminMentorTags.getMentor().getUsuario().getNome()
            : null;
        
        this.tag = adminMentorTags.getTag().getName();
    
    }
}