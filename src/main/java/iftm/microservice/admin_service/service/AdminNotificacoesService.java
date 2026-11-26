package iftm.microservice.admin_service.service;

import com.google.firebase.FirebaseApp;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import iftm.microservice.admin_service.domain.AdminNotificacoes;
import iftm.microservice.admin_service.dto.AdminNotificacoesDTO;
import iftm.microservice.admin_service.dto.NotificacaoEventoDTO;
import iftm.microservice.admin_service.repository.AdminNotificacoesRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class AdminNotificacoesService {

    private final AdminNotificacoesRepository repository;

    @Transactional
    public AdminNotificacoesDTO processarEDisparar(NotificacaoEventoDTO evento) {
        String uuid = (evento.getEventoUuid() != null && !evento.getEventoUuid().isBlank())
                ? evento.getEventoUuid()
                : UUID.randomUUID().toString();

      
        var existente = repository.findByNotificacaoUuid(uuid);
        if (existente.isPresent()) {
            return new AdminNotificacoesDTO(existente.get());
        }

        AdminNotificacoes notificacao = new AdminNotificacoes();
        notificacao.setNotificacaoUuid(uuid);
        notificacao.setTipoNotificacao(evento.getTipoNotificacao());
        notificacao.setUsuarioDestinoUuid(evento.getUsuarioDestinoUuid());
        notificacao.setStatus("PENDENTE");

        notificacao = repository.save(notificacao);

        try {
            String fcmToken = obterTokenFcmDoUsuario(evento);

            if (fcmToken == null || fcmToken.isBlank()) {
                throw new IllegalStateException("Token FCM não encontrado para o usuário: " + evento.getUsuarioDestinoUuid());
            }

            if (FirebaseApp.getApps().isEmpty()) {
                throw new IllegalStateException("Firebase Admin SDK não inicializado (verifique serviceAccountKey.json).");
            }

            Message message = Message.builder()
                    .setToken(fcmToken)
                    .setNotification(Notification.builder()
                            .setTitle(montarTitulo(evento.getTipoNotificacao()))
                            .setBody(montarCorpo(evento.getTipoNotificacao()))
                            .build())
                    .putData("tipoNotificacao", evento.getTipoNotificacao())
                    .putData("notificacaoUuid", uuid)
                    .build();

            String responseId = FirebaseMessaging.getInstance().send(message);
            log.info("Push enviado via Firebase. ID: {}", responseId);

            notificacao.setStatus("ENVIADO");
            notificacao.setDataEnvio(LocalDateTime.now());
            notificacao.setErro(null);

        } catch (Exception e) {
            log.error("Falha ao enviar push para usuário {}: {}", evento.getUsuarioDestinoUuid(), e.getMessage());
            notificacao.setStatus("FALHA");
            notificacao.setErro(e.getMessage());
        }

        notificacao = repository.save(notificacao);
        return new AdminNotificacoesDTO(notificacao);
    }

    @Transactional(readOnly = true)
    public List<AdminNotificacoesDTO> listarPorUsuario(String usuarioUuid) {
        return repository.findByUsuarioDestinoUuidOrderByCriadoEmDesc(usuarioUuid)
                .stream()
                .map(AdminNotificacoesDTO::new)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<AdminNotificacoesDTO> listarTodas() {
        return repository.findAll()
                .stream()
                .map(AdminNotificacoesDTO::new)
                .toList();
    }

    private String obterTokenFcmDoUsuario(NotificacaoEventoDTO evento) {
   
        if (evento.getDados() != null && evento.getDados().get("fcmToken") != null) {
            return evento.getDados().get("fcmToken").toString();
        }
        return null;
    }

    private String montarTitulo(String tipo) {
        return switch (tipo) {
            case "MENTOR_APROVADO" -> "Perfil Aprovado!";
            case "NOVA_MENTORIA_ATRIBUIDA" -> "Nova Mentoria Atribuída";
            case "LEMBRETE_SESSAO_24H" -> "Lembrete de Mentoria (24h)";
            case "LEMBRETE_SESSAO_1H" -> "Sua Mentoria Começa em 1 Hora!";
            case "MENTORIA_EXPIRADA" -> "Mentoria Expirada";
            case "FEEDBACK_DISPONIVEL" -> "Feedback Disponível";
            case "VOUCHER_DISPONIVEL" -> "Novo Voucher Disponível!";
            default -> "Notificação UberHub";
        };
    }

    private String montarCorpo(String tipo) {
        return switch (tipo) {
            case "MENTOR_APROVADO" -> "Parabéns! Seu cadastro como mentor foi aprovado.";
            case "NOVA_MENTORIA_ATRIBUIDA" -> "Você recebeu uma nova mentoria na plataforma.";
            case "LEMBRETE_SESSAO_24H" -> "Sua sessão de mentoria acontecerá em 24 horas.";
            case "LEMBRETE_SESSAO_1H" -> "Prepare-se: sua sessão de mentoria começa em 1 hora.";
            case "MENTORIA_EXPIRADA" -> "Sua mentoria expirou após 7 dias sem agendamento.";
            case "FEEDBACK_DISPONIVEL" -> "Sua sessão foi concluída! Avalie como foi a experiência.";
            case "VOUCHER_DISPONIVEL" -> "Você ganhou um novo voucher de benefício no UberHub.";
            default -> "Você possui uma nova atualização na plataforma UberHub.";
        };
    }
}