package iftm.microservice.admin_service.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import iftm.microservice.admin_service.dto.AdminNotificacoesDTO;
import iftm.microservice.admin_service.dto.NotificacaoEventoDTO;
import iftm.microservice.admin_service.service.AdminNotificacoesService;
import lombok.RequiredArgsConstructor;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController 
@RequestMapping("/api/v1/notificacoes")
@RequiredArgsConstructor 
public class NotificationController {

    private final AdminNotificacoesService service;

    @PostMapping
    public ResponseEntity<AdminNotificacoesDTO> enviarNotificacao(@RequestBody NotificacaoEventoDTO evento) {
        AdminNotificacoesDTO resultado = service.processarEDisparar(evento);
        return ResponseEntity.status(HttpStatus.CREATED).body(resultado);
    }

   
    @GetMapping("/usuario/{usuarioUuid}")
    public ResponseEntity<List<AdminNotificacoesDTO>> listarPorUsuario(@PathVariable String usuarioUuid) {
        return ResponseEntity.ok(service.listarPorUsuario(usuarioUuid));
    }

  
    @GetMapping
    public ResponseEntity<List<AdminNotificacoesDTO>> listarTodas() {
        return ResponseEntity.ok(service.listarTodas());
    }
    
}
