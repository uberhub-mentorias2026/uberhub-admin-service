package iftm.microservice.admin_service.domain;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import iftm.microservice.admin_service.utils.StatusProcessamento;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(
    name = "ADMIN_EVENTOS_PROCESSADOS",
    uniqueConstraints = {
        @UniqueConstraint(name = "UQ_ADMIN_EVENTOS_PROCESSADOS_UUID", columnNames = "EVENTO_UUID")
    },
    indexes = {
        @Index(name = "IX_ADMIN_EVENTOS_PROCESSADOS_STATUS_RECEBIDO", columnList = "STATUS_PROCESSAMENTO, RECEBIDO_EM"),
        @Index(name = "IX_ADMIN_EVENTOS_PROCESSADOS_ORIGEM_TIPO", columnList = "SERVICO_ORIGEM, TIPO_EVENTO")
    }
)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class AdminEventoProcessado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false, updatable = false)
    @EqualsAndHashCode.Include
    private Long id;

    @Column(name = "EVENTO_UUID", length = 36, nullable = false, unique = true, updatable = false, columnDefinition = "CHAR(36)")
    private String eventoUuid;

    @Column(name = "SERVICO_ORIGEM", length = 80, nullable = false)
    private String servicoOrigem;

    @Column(name = "TIPO_EVENTO", length = 120, nullable = false)
    private String tipoEvento;

    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS_PROCESSAMENTO", length = 15, nullable = false)
    @Builder.Default
    private StatusProcessamento statusProcessamento = StatusProcessamento.PROCESSANDO;

    @Column(name = "TENTATIVAS", nullable = false)
    @Builder.Default
    private Integer tentativas = 0;

    @CreationTimestamp
    @Column(name = "RECEBIDO_EM", nullable = false, updatable = false, columnDefinition = "DATETIME(3)")
    private LocalDateTime recebidoEm;

    @Column(name = "PROCESSADO_EM", columnDefinition = "DATETIME(3)")
    private LocalDateTime processadoEm;

    @Column(name = "ERRO", columnDefinition = "TEXT")
    private String erro;

    public void marcarComoProcessado() {
        this.statusProcessamento = StatusProcessamento.PROCESSADO;
        this.processadoEm = LocalDateTime.now();
        this.erro = null;
    }

    public void registrarFalha(String mensagemErro) {
        this.statusProcessamento = StatusProcessamento.FALHA;
        this.tentativas = (this.tentativas == null ? 0 : this.tentativas) + 1;
        this.erro = mensagemErro;
    }
}