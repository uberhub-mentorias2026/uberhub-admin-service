package iftm.microservice.admin_service.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import iftm.microservice.admin_service.domain.AdminMentores;
import iftm.microservice.admin_service.utils.StatusAprovacao;
@Repository
public interface AdminMentoresRepository extends JpaRepository<AdminMentores, Long> {
    @Query("""
        SELECT DISTINCT m
        FROM AdminMentores m
        LEFT JOIN FETCH m.usuario u
        LEFT JOIN FETCH m.mentorTags mt
        LEFT JOIN FETCH mt.tag t
        WHERE m.statusAprovacao = :status
        ORDER BY m.criadoEm ASC
    """)
    List<AdminMentores> findAllByStatusWithDetails(@Param("status") StatusAprovacao status);

    @Query("""
        SELECT DISTINCT m
        FROM AdminMentores m
        LEFT JOIN FETCH m.usuario u
        LEFT JOIN FETCH m.mentorTags mt
        LEFT JOIN FETCH mt.tag t
        WHERE m.id = :id
    """)
    Optional<AdminMentores> findByIdWithDetails(@Param("id") Long id);
}
