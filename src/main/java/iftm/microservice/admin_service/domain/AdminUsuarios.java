package iftm.microservice.admin_service.domain;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.Comment;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import iftm.microservice.admin_service.utils.PerfilUsuario;
import iftm.microservice.admin_service.utils.StatusAcesso;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(
    name = "ADMIN_USUARIOS",
    uniqueConstraints = {
        @UniqueConstraint(name = "UQ_ADMIN_USUARIOS_UUID",  columnNames = "USUARIO_UUID"),
        @UniqueConstraint(name = "UQ_ADMIN_USUARIOS_EMAIL", columnNames = "EMAIL")
    },
    indexes = {
        @Index(name = "IX_ADMIN_USUARIOS_STATUS_PERFIL", columnList = "STATUS_ACESSO, PERFIL")
    }
)
@Comment("Projeção local de usuários do Auth service")
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
public class AdminUsuarios {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @EqualsAndHashCode.Include
    @JdbcTypeCode(SqlTypes.CHAR)
    @Column(name = "USUARIO_UUID", length = 36, nullable = false)
    private UUID usuarioUuid;

    @Column(name = "NOME", length = 160, nullable = false)
    private String nome;

    @Column(name = "EMAIL", length = 254, nullable = false)
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(name = "PERFIL", length = 30, nullable = false)
    private PerfilUsuario perfil;

    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS_ACESSO", length = 20, nullable = false)
    private StatusAcesso statusAcesso = StatusAcesso.ATIVO;

    @Column(name = "MOTIVO_SUSPENSAO", length = 500)
    private String motivoSuspensao;

    @Column(name = "ORIGEM_ATUALIZADO_EM", columnDefinition = "DATETIME(3)")
    private LocalDateTime origemAtualizadoEm;

    @Column(name = "CRIADO_EM", nullable = false, insertable = false, updatable = false,
            columnDefinition = "DATETIME(3)")
    private LocalDateTime criadoEm;

    @Column(name = "ATUALIZADO_EM", nullable = false, insertable = false, updatable = false,
            columnDefinition = "DATETIME(3)")
    private LocalDateTime atualizadoEm;
}