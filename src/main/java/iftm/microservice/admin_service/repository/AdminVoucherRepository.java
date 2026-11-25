package iftm.microservice.admin_service.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import iftm.microservice.admin_service.domain.AdminVoucher;
import iftm.microservice.admin_service.utils.StatusVoucher;

@Repository
public interface AdminVoucherRepository extends JpaRepository<AdminVoucher, Long> {

    Optional<AdminVoucher> findByCodigo(String codigo);

    boolean existsByCodigo(String codigo);

    List<AdminVoucher> findAllByStatus(StatusVoucher status);
}
