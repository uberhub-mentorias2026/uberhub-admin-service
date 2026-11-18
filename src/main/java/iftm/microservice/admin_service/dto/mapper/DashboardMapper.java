package iftm.microservice.admin_service.dto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import iftm.microservice.admin_service.dto.MetricasDashboardDTO;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface DashboardMapper {

    @Mapping(target = "geradoEm", expression = "java(java.time.Instant.now())")
    MetricasDashboardDTO toDto(long novosCadastros,
                               long mentoriasPendentes,
                               long mentoriasRealizadasNoMes,
                               long mentoresPendentes,
                               long vouchersAtivos);
}