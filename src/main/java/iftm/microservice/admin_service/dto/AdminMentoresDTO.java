package iftm.microservice.admin_service.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import iftm.microservice.admin_service.utils.StatusAprovacao;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AdminMentoresDTO {

    private Long id;

    private String mentorUuid;

    private Long usuarioId;

    private String nome;

    private String biografia;

    private StatusAprovacao statusAprovacao = StatusAprovacao.PENDENTE;

    private BigDecimal valorHora;

    private List<String> tags;

    private LocalDateTime origemAtualizadoEm;

    private LocalDateTime criadoEm;

    private LocalDateTime atualizadoEm;
}