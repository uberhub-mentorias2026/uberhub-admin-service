package iftm.microservice.admin_service.repository;

import iftm.microservice.admin_service.domain.AdminNotificacoes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AdminNotificacoesRepository extends JpaRepository<AdminNotificacoes, Long> {

    Optional<AdminNotificacoes> findByNotificacaoUuid(String notificacaoUuid);

    List<AdminNotificacoes> findByUsuarioDestinoUuidOrderByCriadoEmDesc(String usuarioDestinoUuid);
}