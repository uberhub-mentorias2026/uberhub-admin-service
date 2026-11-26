package iftm.microservice.admin_service.dto.mapper;

import java.util.List;

import org.springframework.stereotype.Component;

import iftm.microservice.admin_service.domain.AdminUsuarios;
import iftm.microservice.admin_service.dto.AdminUsuariosDTO;

@Component
public class AdminUsuariosMapper {

    public AdminUsuariosDTO toDto(AdminUsuarios usuario) {
        if (usuario == null) {
            return null;
        }

        AdminUsuariosDTO dto = new AdminUsuariosDTO();
        dto.setId(usuario.getId());
        dto.setUsuarioUuid(usuario.getUsuarioUuid() != null ? usuario.getUsuarioUuid().toString() : null);
        dto.setNome(usuario.getNome());
        dto.setEmail(usuario.getEmail());
        dto.setPerfil(usuario.getPerfil());
        dto.setStatusAcesso(usuario.getStatusAcesso());
        dto.setAprovadoParaMentoria(usuario.getAprovadoParaMentoria());
        dto.setDataAprovacao(usuario.getDataAprovacao());
        dto.setAprovadoPorId(usuario.getAprovadoPorId());
        dto.setMotivoSuspensao(usuario.getMotivoSuspensao());
        dto.setOrigemAtualizadoEm(usuario.getOrigemAtualizadoEm());
        dto.setCriadoEm(usuario.getCriadoEm());
        dto.setAtualizadoEm(usuario.getAtualizadoEm());
        return dto;
    }

    public List<AdminUsuariosDTO> toDtoList(List<AdminUsuarios> usuarios) {
        return usuarios.stream().map(this::toDto).toList();
    }
}