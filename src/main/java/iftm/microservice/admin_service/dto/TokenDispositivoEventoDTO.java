package iftm.microservice.admin_service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


//Entrada do Token FCM do Celular

@Getter 
@Setter 
@NoArgsConstructor 
@AllArgsConstructor 
public class TokenDispositivoEventoDTO {

    private String usuarioUuid;
    private String fcmToken;   
    private String plataforma;
    
}
