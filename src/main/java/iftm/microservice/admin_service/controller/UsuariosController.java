package iftm.microservice.admin_service.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import iftm.microservice.admin_service.dto.AdminUsuariosDTO;
import iftm.microservice.admin_service.dto.SuspenderUsuarioRequestDTO;
import iftm.microservice.admin_service.service.AdminUsuariosService;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/admin/users")
@AllArgsConstructor
@CrossOrigin(origins = "*")
public class UsuariosController {

    private final AdminUsuariosService usuariosService;

    /** GET /admin/users — Listar usuários da plataforma (busca opcional por nome ou e-mail) */
    @GetMapping
    public ResponseEntity<List<AdminUsuariosDTO>> listarUsuarios(
            @RequestParam(required = false) String search) {
        return ResponseEntity.ok(usuariosService.listarUsuarios(search));
    }

    /** POST /admin/users/{id}/suspend — Suspender usuário */
    @PostMapping("/{id}/suspend")
    public ResponseEntity<AdminUsuariosDTO> suspenderUsuario(
            @PathVariable Long id,
            @RequestBody(required = false) SuspenderUsuarioRequestDTO request) {
        String motivo = request == null ? null : request.getMotivo();
        return ResponseEntity.ok(usuariosService.suspenderUsuario(id, motivo));
    }
}