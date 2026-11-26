package iftm.microservice.admin_service.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import iftm.microservice.admin_service.domain.AdminSolicitacaoTroca;
import iftm.microservice.admin_service.dto.AdminSolicitacaoTrocaDTO;
import iftm.microservice.admin_service.dto.AvaliacaoTrocaDTO;
import iftm.microservice.admin_service.dto.mapper.AdminSolicitacaoTrocaMapper;
import iftm.microservice.admin_service.repository.AdminSolicitacaoTrocaRepository;
import iftm.microservice.admin_service.utils.StatusSolicitacaoTroca;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AdminMentoriaService {

    private final AdminSolicitacaoTrocaRepository repository;
    private final AdminSolicitacaoTrocaMapper mapper;

    public List<AdminSolicitacaoTrocaDTO> listarSolicitacoesTroca() {
        return repository.findAllWithDetails().stream()
                .map(mapper::toDto)
                .toList();
    }

    @Transactional
    public AdminSolicitacaoTrocaDTO avaliarSolicitacaoTroca(Long id, AvaliacaoTrocaDTO avaliacao) {
        AdminSolicitacaoTroca solicitacao = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Solicitação não encontrada"));

        if (avaliacao != null && Boolean.FALSE.equals(avaliacao.getAprovado())) {
            solicitacao.setStatusAdmin(StatusSolicitacaoTroca.REPROVADA);
        } else {
            solicitacao.setStatusAdmin(StatusSolicitacaoTroca.APROVADA);
        }

        if (avaliacao != null) {
            solicitacao.setMotivoDecisao(avaliacao.getMotivo());
        }

        solicitacao.setDecididaEm(LocalDateTime.now());

        return mapper.toDto(repository.save(solicitacao));
    }
}