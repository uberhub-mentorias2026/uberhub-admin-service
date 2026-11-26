package iftm.microservice.admin_service.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SuspenderUsuarioRequestDTO {

    private String motivo;
}