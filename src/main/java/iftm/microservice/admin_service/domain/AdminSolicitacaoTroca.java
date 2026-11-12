package iftm.microservice.admin_service.domain;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import iftm.microservice.admin_service.utils.StatusSolicitacaoTroca;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(
    name = "ADMIN_SOLICITACOES_TROCA",
    uniqueConstraints = {
        @UniqueConstraint(name = "UQ_ADMIN_SOLIC_TROCA_UUID", columnNames = "SOLICITACAO_UUID")
    },
    indexes = {
        @Index(name = "IX_ADMIN_SOLIC_TROCA_STATUS_DATA", columnList = "STATUS_ADMIN, CRIADA_EM_ORIGEM"),
        @Index(name = "IX_ADMIN_SOLIC_TROCA_MENTORIA", columnList = "MENTORIA_ID"),
        @Index(name = "IX_ADMIN_SOLIC_TROCA_SOLICITANTE", columnList = "SOLICITANTE_ID")
    }
)
<<<<<<< Updated upstream
=======

>>>>>>> Stashed changes
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
public class AdminSolicitacaoTroca {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @EqualsAndHashCode.Include
    @JdbcTypeCode(SqlTypes.CHAR)
    @Column(name = "SOLICITACAO_UUID", length = 36, nullable = false)
    private UUID solicitacaoUuid;

    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "MENTORIA_ID", nullable = false,
                foreignKey = @ForeignKey(name = "FK_ADMIN_SOLIC_TROCA_MENTORIA"))
    private AdminMentorias mentoria;

    @JdbcTypeCode(SqlTypes.CHAR)
    @Column(name = "SOLICITANTE_UUID", length = 36, nullable = false)
    private UUID solicitanteUuid;

    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SOLICITANTE_ID",
                foreignKey = @ForeignKey(name = "FK_ADMIN_SOLIC_TROCA_SOLICITANTE"))
    private AdminUsuarios solicitante;

    @Column(name = "JUSTIFICATIVA", nullable = false, columnDefinition = "TEXT")
    private String justificativa;

    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS_ADMIN", length = 15, nullable = false)
    private StatusSolicitacaoTroca statusAdmin = StatusSolicitacaoTroca.PENDENTE;

    @Column(name = "MOTIVO_DECISAO", length = 500)
    private String motivoDecisao;

    @Column(name = "CRIADA_EM_ORIGEM", nullable = false, columnDefinition = "DATETIME(3)")
    private LocalDateTime criadaEmOrigem;

    @Column(name = "DECIDIDA_EM", columnDefinition = "DATETIME(3)")
    private LocalDateTime decididaEm;

    @Column(name = "CRIADO_EM", nullable = false, insertable = false, updatable = false,
            columnDefinition = "DATETIME(3)")
    private LocalDateTime criadoEm;

    @Column(name = "ATUALIZADO_EM", nullable = false, insertable = false, updatable = false,
            columnDefinition = "DATETIME(3)")
    private LocalDateTime atualizadoEm;
}