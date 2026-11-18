package iftm.microservice.admin_service.dto;

import java.time.LocalDateTime;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

//Aqui é a entrada de Eventos na fila

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
