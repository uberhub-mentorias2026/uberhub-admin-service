package iftm.microservice.admin_service.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import iftm.microservice.admin_service.domain.AdminTags;
import iftm.microservice.admin_service.utils.StatusTag;

@Repository
public interface AdminTagsRepository extends JpaRepository<AdminTags, Long>{
    
    Optional<AdminTags> findByTagUuid(String tagUuid);
    boolean existsByTagUuid(String tagUuid);

    boolean existsByNameAndCategoria(String name, String categoria);

    List<AdminTags> findByStatusAndCategoria(StatusTag status, String categoria);
    
}
