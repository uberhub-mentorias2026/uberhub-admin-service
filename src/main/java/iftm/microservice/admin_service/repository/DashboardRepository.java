
package iftm.microservice.admin_service.repository;

import java.time.LocalDateTime;

import org.springframework.stereotype.Repository;

import iftm.microservice.admin_service.utils.MentoriaStatus;
import iftm.microservice.admin_service.utils.StatusAprovacao;
import iftm.microservice.admin_service.utils.StatusVoucher;
import jakarta.persistence.EntityManager;
import lombok.AllArgsConstructor;

@Repository 
@AllArgsConstructor 
public class DashboardRepository {
    
    private final EntityManager entityManager;

    public long contarVouchersAtivos(LocalDateTime agora){
            return entityManager.createQuery("""
            SELECT COUNT(v) FROM AdminVoucher v
            WHERE v.status = :status
              AND (v.dataExpiracao IS NULL OR v.dataExpiracao > :agora)
            """, Long.class)
            .setParameter("status", StatusVoucher.ATIVO)
            .setParameter("agora", agora)
            .getSingleResult();
    }

     public long contarMentoresPendentes() {
        return entityManager.createQuery("""
                SELECT COUNT(m) FROM AdminMentores m
                WHERE m.statusAprovacao = :status
                """, Long.class)
                .setParameter("status", StatusAprovacao.PENDENTE)
                .getSingleResult();
    }


    public long contarMentoriasPendentes(){
        return entityManager.createQuery("""
            SELECT COUNT(mentorias) FROM AdminMentorias mentorias
            WHERE mentorias.status = :status
            """, Long.class)
            .setParameter("status", MentoriaStatus.PENDENTE)
            .getSingleResult();

    }

    public long contarNovosCadastros(LocalDateTime desde){
        return entityManager.createQuery("""
            SELECT COUNT(u) FROM AdminUsuarios u
            WHERE u.criadoEm >= :desde
            """,Long.class)
            .setParameter("desde", desde)
            .getSingleResult();
    }


}