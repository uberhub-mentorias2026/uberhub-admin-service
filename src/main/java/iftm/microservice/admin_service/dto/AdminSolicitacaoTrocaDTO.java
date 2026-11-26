package iftm.microservice.admin_service.dto;

import java.time.LocalDateTime;
import java.util.UUID;

import iftm.microservice.admin_service.utils.StatusSolicitacaoTroca;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdminSolicitacaoTrocaDTO {

    private Long id;
    private UUID solicitacaoUuid;

    private Long mentoriaId;
    private UUID mentoriaUuid;
    private String mentorAtualNome;

    private Long solicitanteId;
    private UUID solicitanteUuid;
    private String solicitanteNome;

    private String justificativa;
    private StatusSolicitacaoTroca statusAdmin;
    private String motivoDecisao;

    private LocalDateTime criadaEmOrigem;
    private LocalDateTime decididaEm;
    private LocalDateTime criadoEm;
    private LocalDateTime atualizadoEm;
}
