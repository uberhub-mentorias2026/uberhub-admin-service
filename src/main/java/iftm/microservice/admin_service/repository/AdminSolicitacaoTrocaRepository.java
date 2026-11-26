package iftm.microservice.admin_service.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import iftm.microservice.admin_service.domain.AdminSolicitacaoTroca;

@Repository
public interface AdminSolicitacaoTrocaRepository extends JpaRepository<AdminSolicitacaoTroca, Long> {

    @Query("""
        SELECT s FROM AdminSolicitacaoTroca s
        LEFT JOIN FETCH s.solicitante
        LEFT JOIN FETCH s.mentoria m
        LEFT JOIN FETCH m.mentor mentor
        LEFT JOIN FETCH mentor.usuario
        ORDER BY s.criadaEmOrigem DESC
    """)
    List<AdminSolicitacaoTroca> findAllWithDetails();
}