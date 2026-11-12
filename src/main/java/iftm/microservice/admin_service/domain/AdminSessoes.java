package iftm.microservice.admin_service.domain;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import iftm.microservice.admin_service.utils.StatusSessao;
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
    name = "ADMIN_SESSOES",
    uniqueConstraints = {
        @UniqueConstraint(name = "UQ_ADMIN_SESSOES_UUID", columnNames = "SESSAO_UUID")
    },
    indexes = {
        @Index(name = "IX_ADMIN_SESSOES_MENTORIA_DATA", columnList = "MENTORIA_ID, DATA_AGENDADA"),
        @Index(name = "IX_ADMIN_SESSOES_STATUS_DATA", columnList = "STATUS, DATA_AGENDADA")
    }
)
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
public class AdminSessoes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @EqualsAndHashCode.Include
    @JdbcTypeCode(SqlTypes.CHAR)
    @Column(name = "SESSAO_UUID", length = 36, nullable = false)
    private UUID sessaoUuid;

    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "MENTORIA_ID", nullable = false,
                foreignKey = @ForeignKey(name = "FK_ADMIN_SESSOES_MENTORIA"))
    private AdminMentorias mentoria;

    @Column(name = "DATA_AGENDADA", nullable = false, columnDefinition = "DATETIME(3)")
    private LocalDateTime dataAgendada;

    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS", length = 20, nullable = false)
    private StatusSessao status = StatusSessao.AGENDADA;

    @Column(name = "MENTOR_COMPARECEU")
    private Boolean mentorCompareceu;

    @Column(name = "MENTORADO_COMPARECEU")
    private Boolean mentoradoCompareceu;

    @Column(name = "DATA_CONCLUSAO", columnDefinition = "DATETIME(3)")
    private LocalDateTime dataConclusao;

    @Column(name = "ORIGEM_ATUALIZADO_EM", columnDefinition = "DATETIME(3)")
    private LocalDateTime origemAtualizadoEm;

    @Column(name = "CRIADO_EM", nullable = false, insertable = false, updatable = false,
            columnDefinition = "DATETIME(3)")
    private LocalDateTime criadoEm;

    @Column(name = "ATUALIZADO_EM", nullable = false, insertable = false, updatable = false,
            columnDefinition = "DATETIME(3)")
    private LocalDateTime atualizadoEm;
}