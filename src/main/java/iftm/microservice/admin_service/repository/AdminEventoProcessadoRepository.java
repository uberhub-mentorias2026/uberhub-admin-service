package iftm.microservice.admin_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import iftm.microservice.admin_service.domain.AdminEventoProcessado;

@Repository
public interface AdminEventoProcessadoRepository extends JpaRepository<AdminEventoProcessado, Long> {
    
}
