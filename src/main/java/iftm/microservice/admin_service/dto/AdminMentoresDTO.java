
package iftm.microservice.admin_service.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import iftm.microservice.admin_service.utils.StatusAprovacao;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AdminMentoresDTO {

    private Long id;

    private UUID mentorUuid;

    private Long usuarioId;

    private String biografia;

    private StatusAprovacao statusAprovacao = StatusAprovacao.PENDENTE;

    private BigDecimal valorHora;

    private LocalDateTime origemAtualizadoEm;

    private LocalDateTime criadoEm;

    private LocalDateTime atualizadoEm;
}
