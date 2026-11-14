package iftm.microservice.admin_service.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import iftm.microservice.admin_service.domain.AdminEventoProcessado;
import iftm.microservice.admin_service.utils.StatusProcessamento;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdminEventoProcessadoRequestDTO {

    @NotBlank(message = "O UUID do evento é obrigatório")
    @Size(min = 36, max = 36, message = "O UUID deve conter exatamente 36 caracteres")
    private String eventoUuid;

    @NotBlank(message = "O serviço de origem é obrigatório")
    @Size(max = 80, message = "O serviço de origem deve ter no máximo 80 caracteres")
    private String servicoOrigem;

    @NotBlank(message = "O tipo do evento é obrigatório")
    @Size(max = 120, message = "O tipo do evento deve ter no máximo 120 caracteres")
    private String tipoEvento;

    public AdminEventoProcessado toEntity() {
        return AdminEventoProcessado.builder()
                .eventoUuid(this.eventoUuid)
                .servicoOrigem(this.servicoOrigem)
                .tipoEvento(this.tipoEvento)
                .statusProcessamento(StatusProcessamento.PROCESSANDO)
                .tentativas(0)
                .build();
    }
}