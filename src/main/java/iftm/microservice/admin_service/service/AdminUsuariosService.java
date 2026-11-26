package iftm.microservice.admin_service.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ResponseStatusException;

import iftm.microservice.admin_service.domain.AdminUsuarios;
import iftm.microservice.admin_service.dto.AdminUsuariosDTO;
import iftm.microservice.admin_service.dto.mapper.AdminUsuariosMapper;
import iftm.microservice.admin_service.repository.AdminUsuariosRepository;
import iftm.microservice.admin_service.utils.StatusAcesso;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AdminUsuariosService {

    private static final int MOTIVO_MAX = 500;

    private final AdminUsuariosRepository usuariosRepository;
    private final AdminUsuariosMapper usuariosMapper;

    /** Lista todos os usuários, ou filtra por nome/e-mail quando houver busca */
    @Transactional(readOnly = true)
    public List<AdminUsuariosDTO> listarUsuarios(String search) {
        List<AdminUsuarios> usuarios = StringUtils.hasText(search)
                ? usuariosRepository.buscarPorNomeOuEmail(search.trim())
                : usuariosRepository.findAllByOrderByNomeAsc();

        return usuariosMapper.toDtoList(usuarios);
    }

    /** ATIVO → SUSPENSO */
    @Transactional
    public AdminUsuariosDTO suspenderUsuario(Long id, String motivo) {
        if (!StringUtils.hasText(motivo)) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "O motivo da suspensão é obrigatório.");
        }
        if (motivo.trim().length() > MOTIVO_MAX) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "O motivo deve ter no máximo " + MOTIVO_MAX + " caracteres.");
        }

        AdminUsuarios usuario = usuariosRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Usuário não encontrado com ID: " + id));

        if (usuario.getStatusAcesso() != StatusAcesso.ATIVO) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Apenas usuários com status ATIVO podem ser suspensos.");
        }

        usuario.setStatusAcesso(StatusAcesso.SUSPENSO);
        usuario.setMotivoSuspensao(motivo.trim());
        AdminUsuarios salvo = usuariosRepository.save(usuario);

        // TODO: avisar o Auth service para bloquear o login de fato
        return usuariosMapper.toDto(salvo);
    }
}