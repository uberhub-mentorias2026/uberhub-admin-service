package iftm.microservice.admin_service.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import iftm.microservice.admin_service.domain.AdminMentorTagId;
import iftm.microservice.admin_service.domain.AdminMentorTags; // <-- Agora dá para importar!

@Repository 
public interface AdminMentorTagsRepository extends JpaRepository<AdminMentorTags, AdminMentorTagId> {
    
    List<AdminMentorTags> findByMentorId(Long mentorId);
    List<AdminMentorTags> findByTagId(Long tagId);

    boolean existsByMentorIdAndTagId(Long mentorId, Long tagId);
    
}