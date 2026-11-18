package iftm.microservice.admin_service.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import iftm.microservice.admin_service.domain.AdminMentores;
import iftm.microservice.admin_service.dto.AdminMentoresDTO;
import iftm.microservice.admin_service.dto.AvaliacaoMentorDTO;
import iftm.microservice.admin_service.dto.mapper.AdminMentorMapper;
import iftm.microservice.admin_service.repository.AdminMentoresRepository;
import iftm.microservice.admin_service.utils.StatusAprovacao;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class MentorService {

    private final AdminMentoresRepository mentoresRepository;
    private final AdminMentorMapper mentorMapper;

    public List<AdminMentoresDTO> buscarMentoresPendentes() {
        List<AdminMentores> pendentes = mentoresRepository.findAllByStatusWithDetails(StatusAprovacao.PENDENTE);
        return mentorMapper.toDtoList(pendentes);
    }

    @Transactional
    public AdminMentoresDTO avaliarMentor(Long id, AvaliacaoMentorDTO avaliacao) {
        // 1. Busca o mentor no banco
        AdminMentores mentor = mentoresRepository.findByIdWithDetails(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Mentor não encontrado com ID: " + id));

        // 2. Garante que só mentores pendentes possam ser avaliados
        if (mentor.getStatusAprovacao() != StatusAprovacao.PENDENTE) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Apenas mentores com status PENDENTE podem ser avaliados.");
        }

        // 3. Atualiza o status conforme a decisão do Admin
        if (avaliacao.isAprovado()) {
            mentor.setStatusAprovacao(StatusAprovacao.APROVADO);
        } else {
            mentor.setStatusAprovacao(StatusAprovacao.REPROVADO);
        }

        // 4. Salva e converte para DTO
        AdminMentores salvo = mentoresRepository.save(mentor);
        return mentorMapper.toDto(salvo);
    }
}