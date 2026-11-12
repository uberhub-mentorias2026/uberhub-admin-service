package iftm.microservice.admin_service.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;

@Entity
@Table(name = "ADMIN_NOTIFICACOES")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor 
public class AdminNotificacoes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "NOTIFICACAO_UUID", nullable = false, unique = true, length = 36)
    private String notificacaoUuid;

    @Column(name = "TIPO_NOTIFICACAO", nullable = false, length = 80)
    private String tipoNotificacao;

    @Column(name = "USUARIO_DESTINO_UUID", nullable = false, length = 36)
    private String usuarioDestinoUuid;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USUARIO_ID")
    private AdminUsuarios usuario;

    @Column(name = "STATUS", nullable = false, length = 15)
    private String status;

    @Column(name = "DATA_ENVIO")
    private LocalDateTime dataEnvio;

    @Column(name = "ERRO", columnDefinition = "TEXT")
    private String erro;

    @Column(name = "CRIADO_EM", nullable = false, updatable = false)
    private LocalDateTime criadoEm;

    @Column(name = "ATUALIZADO_EM", nullable = false)
    private LocalDateTime atualizadoEm;

    @PrePersist
    protected void onCreate() {
        LocalDateTime now = LocalDateTime.now();
        criadoEm = now;
        atualizadoEm = now;
        if (status == null) {
            status = "PENDENTE";
        }
    }

    @PreUpdate
    protected void onUpdate() {
        atualizadoEm = LocalDateTime.now();
    }
}