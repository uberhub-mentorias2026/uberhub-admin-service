package iftm.microservice.admin_service.dto;

import java.time.LocalDateTime;

import iftm.microservice.admin_service.utils.PerfilUsuario;
import iftm.microservice.admin_service.utils.StatusAcesso;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AdminUsuariosDTO {

    private Long id;

    private String usuarioUuid;

    private String nome;

    private String email;

    private PerfilUsuario perfil;

    private StatusAcesso statusAcesso = StatusAcesso.ATIVO;

    private Boolean aprovadoParaMentoria;

    private LocalDateTime dataAprovacao;

    private Long aprovadoPorId;

    private String motivoSuspensao;

    private LocalDateTime origemAtualizadoEm;

    private LocalDateTime criadoEm;

    private LocalDateTime atualizadoEm;
}