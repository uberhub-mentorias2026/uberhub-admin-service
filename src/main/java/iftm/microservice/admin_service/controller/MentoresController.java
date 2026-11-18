package iftm.microservice.admin_service.controller;

import java.util.List;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import iftm.microservice.admin_service.domain.AdminMentores;

import iftm.microservice.admin_service.dto.AdminMentoresDTO;
import iftm.microservice.admin_service.dto.mapper.AdminMentorMapper;
import iftm.microservice.admin_service.repository.AdminMentoresRepository;
import iftm.microservice.admin_service.utils.StatusAprovacao;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@RestController
@RequestMapping("/admin/mentors")
@AllArgsConstructor
@CrossOrigin(origins = "*")
public class MentoresController {

    private final AdminMentoresRepository mentoresRepository;
    private final AdminMentorMapper mentorMapper;

    @GetMapping("/pending")
    public ResponseEntity<List<AdminMentoresDTO>> buscarMentoresPendentes() {
        List<AdminMentores> pendentes = mentoresRepository.findAllByStatusWithDetails(StatusAprovacao.PENDENTE);

        return ResponseEntity.ok(mentorMapper.toDtoList(pendentes));

    }

    @PostMapping("/{id}/approve")
    public ResponseEntity<AdminMentoresDTO> aprovarMentor(@PathVariable Long id) {
        AdminMentores mentor = mentoresRepository.findByIdWithDetails(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Mentor não encontrado com ID: " + id));

        if (mentor.getStatusAprovacao() != StatusAprovacao.PENDENTE) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Apenas mentores com status PENDENTE podem ser aprovados.");
        }

        mentor.setStatusAprovacao(StatusAprovacao.APROVADO);
        AdminMentores salvo = mentoresRepository.save(mentor);

        return ResponseEntity.ok(mentorMapper.toDto(salvo));
    }

}
