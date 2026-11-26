package iftm.microservice.admin_service.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import iftm.microservice.admin_service.domain.AdminUsuarios;

@Repository
public interface AdminUsuariosRepository extends JpaRepository<AdminUsuarios, Long> {

    Optional<AdminUsuarios> findByUsuarioUuid(UUID usuarioUuid);

    List<AdminUsuarios> findAllByOrderByNomeAsc();

    @Query("""
            SELECT u FROM AdminUsuarios u
            WHERE LOWER(u.nome)  LIKE LOWER(CONCAT('%', :termo, '%'))
               OR LOWER(u.email) LIKE LOWER(CONCAT('%', :termo, '%'))
            ORDER BY u.nome ASC
            """)
    List<AdminUsuarios> buscarPorNomeOuEmail(@Param("termo") String termo);
}