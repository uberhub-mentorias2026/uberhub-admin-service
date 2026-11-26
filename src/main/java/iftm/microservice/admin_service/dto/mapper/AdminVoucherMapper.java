package iftm.microservice.admin_service.dto.mapper;

import java.util.List;

import org.mapstruct.*;

import iftm.microservice.admin_service.domain.AdminVoucher;
import iftm.microservice.admin_service.dto.AdminVoucherDTO;

@Mapper(componentModel = "spring", builder = @Builder(disableBuilder = true))
public interface AdminVoucherMapper {

    AdminVoucher toEntity(AdminVoucherDTO adminVoucherDTO);
 
    AdminVoucherDTO toDto(AdminVoucher adminVoucher);
 
    List<AdminVoucher> toEntityList(List<AdminVoucherDTO> adminVoucherDTOs);
 
    List<AdminVoucherDTO> toDtoList(List<AdminVoucher> adminVouchers);
 
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "criadoEm", ignore = true)
    @Mapping(target = "atualizadoEm", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntity(AdminVoucherDTO adminVoucherDTO, @MappingTarget AdminVoucher adminVoucher);
}
