package iftm.microservice.admin_service.domain;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.type.SqlTypes;

import iftm.microservice.admin_service.utils.MentoriaStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data 
@AllArgsConstructor 
@NoArgsConstructor 
@Builder
@Table(name = "ADMIN_MENTORIAS")
public class AdminMentorias {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column (name = "ID")
    private Long id;

    @JdbcTypeCode(SqlTypes.CHAR)
    @Column(name = "MENTORIA_UUID", nullable = false, unique = true, length = 36)
    private UUID mentoriaUuid;

    @JdbcTypeCode(SqlTypes.CHAR)
    @Column(name = "MENTORADO_UUID", nullable = false, length = 36)
    private UUID mentoradoUuid;

    @JdbcTypeCode(SqlTypes.CHAR)
    @Column(name = "MENTOR_UUID", nullable = false, length = 36)
    private UUID mentorUuid;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MENTORADO_ID", foreignKey = @ForeignKey(name = "FK_ADMIN_MENTORIAS_MENTORADO"))
    private AdminUsuarios mentorado;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MENTOR_ID", foreignKey = @ForeignKey(name = "FK_ADMIN_MENTORIAS_MENTOR"))
    private AdminMentores mentor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "VOUCHER_ID", foreignKey = @ForeignKey(name = "FK_ADMIN_MENTORIAS_VOUCHER"))
    private AdminVoucher voucher;

    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS", nullable = false, length = 20)
    @Builder.Default
    private MentoriaStatus status = MentoriaStatus.PENDENTE;

    @Column(name = "DATA_CRIACAO_ORIGEM", nullable = false)
    private LocalDateTime dataCriacaoOrigem;

    @Column(name = "DATA_AGENDADA")
    private LocalDateTime dataAgendada;

    @Column(name = "ORIGEM_ATUALIZADO_EM")
    private LocalDateTime origemAtualizadoEm;

    @CreationTimestamp
    @Column(name = "CRIADO_EM", nullable = false, updatable = false)
    private LocalDateTime criadoEm;

    @UpdateTimestamp
    @Column(name = "ATUALIZADO_EM", nullable = false)
    private LocalDateTime atualizadoEm;
}
