package iftm.microservice.admin_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import iftm.microservice.admin_service.domain.AdminMentorias;

@Repository
public interface AdminMentoriasRepository extends JpaRepository<AdminMentorias, Long> {
    
}
