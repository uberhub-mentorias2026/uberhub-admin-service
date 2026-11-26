package iftm.microservice.admin_service.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import iftm.microservice.admin_service.dto.AdminSolicitacaoTrocaDTO;
import iftm.microservice.admin_service.dto.AvaliacaoTrocaDTO;
import iftm.microservice.admin_service.service.AdminMentoriaService;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/admin/mentorships")
@AllArgsConstructor
@CrossOrigin(origins = "*")
public class MentoriasController {

    private final AdminMentoriaService mentoriaService;

    @GetMapping("/change-requests")
    public ResponseEntity<List<AdminSolicitacaoTrocaDTO>> listarSolicitacoesTroca() {
        return ResponseEntity.ok(mentoriaService.listarSolicitacoesTroca());
    }

    @PostMapping("/change-requests/{id}/approve")
    public ResponseEntity<AdminSolicitacaoTrocaDTO> avaliarSolicitacaoTroca(
            @PathVariable Long id,
            @RequestBody(required = false) AvaliacaoTrocaDTO avaliacao) {
        return ResponseEntity.ok(mentoriaService.avaliarSolicitacaoTroca(id, avaliacao));
    }
}