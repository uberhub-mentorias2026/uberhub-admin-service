package iftm.microservice.admin_service.dto;

import java.time.LocalDateTime;

import iftm.microservice.admin_service.utils.StatusVoucher;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AdminVoucherDTO {
    private Long id;

    @Size(min = 14, max = 14, message = "Deve seguir o padrão: XXX-XXXX-XXXX inclusive os hifens")
    @NotBlank(message = "O código do voucher é obrigatório.")
    private String codigo;

    private StatusVoucher status;

    private LocalDateTime dataExpiracao;

    private LocalDateTime criadoEm;

    private LocalDateTime atualizadoEm;   
}
