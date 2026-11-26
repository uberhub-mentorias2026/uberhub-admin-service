package iftm.microservice.admin_service.domain;


import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "ADMIN_FEEDBACKS_MENTOR")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdminFeedbackMentor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false, updatable = false)
    private Long id;

    @Column(name = "FEEDBACK_UUID", nullable = false, unique = true, length = 36, columnDefinition = "CHAR(36)")
    private String feedbackUuid;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "MENTORIA_ID", nullable = false, unique = true)
    private AdminMentorias mentoria;

    @Column(name = "MENTORADO_UUID", nullable = false, length = 36, columnDefinition = "CHAR(36)")
    private String mentoradoUuid;

    @Builder.Default
    @Column(name = "TEVE_NO_SHOW", nullable = false)
    private Boolean teveNoShow = false;

    @Min(0)
    @Max(10)
    @Column(name = "NIVEL_ENGAJAMENTO", columnDefinition = "TINYINT UNSIGNED")
    private Integer nivelEngajamento;

    @Column(name = "ALERTA_COMPORTAMENTO", columnDefinition = "TEXT")
    private String alertaComportamento;

    @Column(name = "ENVIADO_EM", nullable = false, columnDefinition = "DATETIME(3)")
    private LocalDateTime enviadoEm;

    @CreationTimestamp
    @Column(name = "CRIADO_EM", nullable = false, updatable = false, columnDefinition = "DATETIME(3)")
    private LocalDateTime criadoEm;

    @UpdateTimestamp
    @Column(name = "ATUALIZADO_EM", nullable = false, columnDefinition = "DATETIME(3)")
    private LocalDateTime atualizadoEm;
}