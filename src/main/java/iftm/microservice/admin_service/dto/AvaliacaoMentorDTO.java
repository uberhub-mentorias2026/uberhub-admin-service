package iftm.microservice.admin_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AvaliacaoMentorDTO {
    private boolean aprovado; 
    private String motivo;    
}