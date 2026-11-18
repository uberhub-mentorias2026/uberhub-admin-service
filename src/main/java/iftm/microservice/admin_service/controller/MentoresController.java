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

import iftm.microservice.admin_service.dto.AdminMentoresDTO;
import iftm.microservice.admin_service.dto.AvaliacaoMentorDTO;
import iftm.microservice.admin_service.service.MentorService;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/admin/mentors")
@AllArgsConstructor
@CrossOrigin(origins = "*")
public class MentoresController {

    private final MentorService mentorService;

    @GetMapping("/pending")
    public ResponseEntity<List<AdminMentoresDTO>> buscarMentoresPendentes() {
        return ResponseEntity.ok(mentorService.buscarMentoresPendentes());
    }

    @PostMapping("/{id}/approve")
    public ResponseEntity<AdminMentoresDTO> avaliarMentor(
            @PathVariable Long id, 
            @RequestBody AvaliacaoMentorDTO avaliacao) {
        
        return ResponseEntity.ok(mentorService.avaliarMentor(id, avaliacao));
    }
}