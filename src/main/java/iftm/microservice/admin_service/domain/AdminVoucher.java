package iftm.microservice.admin_service.domain;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import iftm.microservice.admin_service.utils.StatusVoucher;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

@Entity 
@Table(name="ADMIN_VOUCHERS")
@NoArgsConstructor 
@AllArgsConstructor 
@Builder
@Data 
public class AdminVoucher {
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="ID")
    private Long id;

    @Column(name = "CODIGO", nullable = false, unique = true, length = 14)
    @Size(min= 14, max= 14, message = "Deve seguir o padrão: XXX-XXXX-XXXX inclusive os hifens")
    @NotBlank
    private String codigo;

    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS", nullable = false, length = 15)
    @Builder.Default
    private StatusVoucher status = StatusVoucher.ATIVO;

    @Column(name = "DATA_EXPIRACAO")
    private LocalDateTime dataExpiracao;

    @CreationTimestamp
    @Column(name = "CRIADO_EM", nullable = false, updatable = false)
    private LocalDateTime criadoEm;

    @UpdateTimestamp
    @Column(name = "ATUALIZADO_EM", nullable = false)
    private LocalDateTime atualizadoEm;

}