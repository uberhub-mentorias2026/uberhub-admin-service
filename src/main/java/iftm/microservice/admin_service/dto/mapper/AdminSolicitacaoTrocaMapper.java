package iftm.microservice.admin_service.dto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import iftm.microservice.admin_service.domain.AdminSolicitacaoTroca;
import iftm.microservice.admin_service.dto.AdminSolicitacaoTrocaDTO;

@Mapper(componentModel = "spring")
public interface AdminSolicitacaoTrocaMapper {

    @Mapping(source = "mentoria.id", target = "mentoriaId")
    @Mapping(source = "mentoria.mentoriaUuid", target = "mentoriaUuid")
    @Mapping(source = "mentoria.mentor.usuario.nome", target = "mentorAtualNome")
    @Mapping(source = "solicitante.id", target = "solicitanteId")
    @Mapping(source = "solicitante.nome", target = "solicitanteNome")
    AdminSolicitacaoTrocaDTO toDto(AdminSolicitacaoTroca entity);
}