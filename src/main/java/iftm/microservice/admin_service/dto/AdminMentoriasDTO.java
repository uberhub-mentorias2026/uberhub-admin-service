package iftm.microservice.admin_service.dto;

import java.time.LocalDateTime;
import java.util.UUID;

import iftm.microservice.admin_service.utils.MentoriaStatus;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdminMentoriasDTO {
    
    private Long id;

    @NotNull(message = "O UUID da mentoria é obrigatório.")
    private UUID mentoriaUuid;

    @NotNull(message = "O UUID do mentorado é obrigatório.")
    private UUID mentoradoUuid;

    @NotNull(message = "O UUID do mentor é obrigatório.")
    private UUID mentorUuid;

  
    private Long mentoradoId;
    private Long mentorId;
    private Long voucherId;

    private MentoriaStatus status;

    @NotNull(message = "A data de criação na origem é obrigatória.")
    private LocalDateTime dataCriacaoOrigem;

    private LocalDateTime dataAgendada;

    private LocalDateTime origemAtualizadoEm;


    private LocalDateTime criadoEm;
    private LocalDateTime atualizadoEm;
}