package iftm.microservice.admin_service.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import iftm.microservice.admin_service.utils.StatusAprovacao;
import jakarta.annotation.Generated;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity 
@Data 
@Table(name = "ADMIN_MENTORES")
@AllArgsConstructor 
@NoArgsConstructor 
public class AdminMentores {
    
    @Id 
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;

    @JdbcTypeCode(SqlTypes.CHAR)
    @Column(name = "MENTOR_UUID", length = 36, nullable = false)
    private String mentorUuid;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(
        name = "USUARIO_ID",
        foreignKey = @ForeignKey(name = "FK_ADMIN_MENTORES_USUARIO")
    )
    private AdminUsuario usuario;

    @Column(name = "BIOGRAFIA", columnDefinition = "TEXT")
    private String biografia;
       
    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS_APROVACAO", length = 20, nullable = false)
    private StatusAprovacao statusAprovacao = StatusAprovacao.PENDENTE;

    @Column(name = "VALOR_HORA", precision = 10, scale = 2)
    private BigDecimal valorHora;

    @Column(name = "ORIGEM_ATUALIZADO_EM")
    private LocalDateTime origemAtualizadoEm;

    @Column(name = "CRIADO_EM", nullable = false)
    private LocalDateTime criadoEm;

    @Column(name = "ATUALIZADO_EM", nullable = false)
    private LocalDateTime atualizadoEm;

}
