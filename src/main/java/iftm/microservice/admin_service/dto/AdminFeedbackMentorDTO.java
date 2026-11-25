package iftm.microservice.admin_service.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdminFeedbackMentorDTO {

    private Long id;
    private String feedbackUuid;
    private Long mentoriaId;
    private String mentoradoUuid;
    private Boolean teveNoShow;
    private Integer nivelEngajamento;
    private String alertaComportamento;
    private LocalDateTime enviadoEm;
    private LocalDateTime criadoEm;
    private LocalDateTime atualizadoEm;
}