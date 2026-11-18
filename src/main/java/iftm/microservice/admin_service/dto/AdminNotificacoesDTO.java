package iftm.microservice.admin_service.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter 
@Setter 
@NoArgsConstructor 
@AllArgsConstructor 

public class AdminNotificacoesDTO {
    private String notificacaoUuid;
    private String tipoNotificacao;
    private String usuarioDestinoUuid;
    private String status;
    private LocalDateTime dataEnvio;
    private String erro;
    private LocalDateTime criadoEm;

    public AdminNotificacoesDTO(AdminNotificacoesDTO entity) {
        this.notificacaoUuid = entity.getNotificacaoUuid();
        this.tipoNotificacao = entity.getTipoNotificacao();
        this.usuarioDestinoUuid = entity.getUsuarioDestinoUuid();
        this.status = entity.getStatus();
        this.dataEnvio = entity.getDataEnvio();
        this.erro = entity.getErro();
        this.criadoEm = entity.getCriadoEm();
    }
}
