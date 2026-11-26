package iftm.microservice.admin_service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NotificacaoEventoDTO {

    private String eventoUuid;          
    private String servicoOrigem;       
    private String tipoNotificacao;     
    private String usuarioDestinoUuid;  
    private LocalDateTime dataHora;     
    private Map<String, Object> dados;  
}