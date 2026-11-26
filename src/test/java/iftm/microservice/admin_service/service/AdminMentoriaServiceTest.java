package iftm.microservice.admin_service.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import iftm.microservice.admin_service.domain.AdminSolicitacaoTroca;
import iftm.microservice.admin_service.dto.AdminSolicitacaoTrocaDTO;
import iftm.microservice.admin_service.dto.AvaliacaoTrocaDTO;
import iftm.microservice.admin_service.dto.mapper.AdminSolicitacaoTrocaMapper;
import iftm.microservice.admin_service.repository.AdminSolicitacaoTrocaRepository;
import iftm.microservice.admin_service.utils.StatusSolicitacaoTroca;

@ExtendWith(MockitoExtension.class)
public class AdminMentoriaServiceTest {

    @Mock
    private AdminSolicitacaoTrocaRepository repository;

    @Mock
    private AdminSolicitacaoTrocaMapper mapper;

    @InjectMocks
    private AdminMentoriaService service;

    private AdminSolicitacaoTroca solicitacaoPendente;

    @BeforeEach
    void setUp() {
        solicitacaoPendente = AdminSolicitacaoTroca.builder()
                .id(1L)
                .solicitacaoUuid(UUID.randomUUID())
                .justificativa("Preciso de mentor com foco em Spring Boot.")
                .statusAdmin(StatusSolicitacaoTroca.PENDENTE)
                .build();
    }

    @Test
    @DisplayName("Deve listar todas as solicitações de troca convertidas para DTO")
    void deveListarSolicitacoesTroca() {
        AdminSolicitacaoTrocaDTO dto = AdminSolicitacaoTrocaDTO.builder()
                .id(1L)
                .statusAdmin(StatusSolicitacaoTroca.PENDENTE)
                .build();

        when(repository.findAllWithDetails()).thenReturn(List.of(solicitacaoPendente));
        when(mapper.toDto(solicitacaoPendente)).thenReturn(dto);

        List<AdminSolicitacaoTrocaDTO> resultado = service.listarSolicitacoesTroca();

        assertEquals(1, resultado.size());
        assertEquals(StatusSolicitacaoTroca.PENDENTE, resultado.get(0).getStatusAdmin());
        verify(repository).findAllWithDetails();
    }

    @Test
    @DisplayName("Deve aprovar solicitação quando aprovado for true")
    void deveAprovarSolicitacaoTroca() {
        AvaliacaoTrocaDTO avaliacao = new AvaliacaoTrocaDTO(true, "Troca autorizada.");
        AdminSolicitacaoTrocaDTO dtoEsperado = AdminSolicitacaoTrocaDTO.builder()
                .id(1L)
                .statusAdmin(StatusSolicitacaoTroca.APROVADA)
                .motivoDecisao("Troca autorizada.")
                .build();

        when(repository.findById(1L)).thenReturn(Optional.of(solicitacaoPendente));
        when(repository.save(any(AdminSolicitacaoTroca.class))).thenAnswer(inv -> inv.getArgument(0));
        when(mapper.toDto(any(AdminSolicitacaoTroca.class))).thenReturn(dtoEsperado);

        AdminSolicitacaoTrocaDTO resultado = service.avaliarSolicitacaoTroca(1L, avaliacao);

        assertEquals(StatusSolicitacaoTroca.APROVADA, solicitacaoPendente.getStatusAdmin());
        assertEquals("Troca autorizada.", solicitacaoPendente.getMotivoDecisao());
        assertNotNull(solicitacaoPendente.getDecididaEm());
        assertEquals(StatusSolicitacaoTroca.APROVADA, resultado.getStatusAdmin());
        verify(repository).save(solicitacaoPendente);
    }

    @Test
    @DisplayName("Deve reprovar solicitação quando aprovado for false")
    void deveReprovarSolicitacaoTroca() {
        AvaliacaoTrocaDTO avaliacao = new AvaliacaoTrocaDTO(false, "Justificativa insuficiente.");
        AdminSolicitacaoTrocaDTO dtoEsperado = AdminSolicitacaoTrocaDTO.builder()
                .id(1L)
                .statusAdmin(StatusSolicitacaoTroca.REPROVADA)
                .motivoDecisao("Justificativa insuficiente.")
                .build();

        when(repository.findById(1L)).thenReturn(Optional.of(solicitacaoPendente));
        when(repository.save(any(AdminSolicitacaoTroca.class))).thenAnswer(inv -> inv.getArgument(0));
        when(mapper.toDto(any(AdminSolicitacaoTroca.class))).thenReturn(dtoEsperado);

        AdminSolicitacaoTrocaDTO resultado = service.avaliarSolicitacaoTroca(1L, avaliacao);

        assertEquals(StatusSolicitacaoTroca.REPROVADA, solicitacaoPendente.getStatusAdmin());
        assertEquals("Justificativa insuficiente.", solicitacaoPendente.getMotivoDecisao());
        assertNotNull(solicitacaoPendente.getDecididaEm());
        assertEquals(StatusSolicitacaoTroca.REPROVADA, resultado.getStatusAdmin());
    }

    @Test
    @DisplayName("Deve lançar exceção quando o ID da solicitação não existir")
    void deveLancarExcecaoQuandoNaoEncontrarSolicitacao() {
        when(repository.findById(99L)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> service.avaliarSolicitacaoTroca(99L, new AvaliacaoTrocaDTO(true, "Ok")));

        assertEquals("Solicitação não encontrada", ex.getMessage());
        verify(repository, never()).save(any());
    }
}