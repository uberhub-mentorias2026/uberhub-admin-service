package iftm.microservice.admin_service.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import iftm.microservice.admin_service.domain.AdminUsuarios;
import iftm.microservice.admin_service.dto.AdminUsuariosDTO;
import iftm.microservice.admin_service.dto.mapper.AdminUsuariosMapper;
import iftm.microservice.admin_service.repository.AdminUsuariosRepository;
import iftm.microservice.admin_service.utils.PerfilUsuario;
import iftm.microservice.admin_service.utils.StatusAcesso;

@ExtendWith(MockitoExtension.class)
class AdminUsuariosServiceTest {

    @Mock
    private AdminUsuariosRepository usuariosRepository;

    private AdminUsuariosService service;

    @BeforeAll
    static void inicio() {
        System.out.println();
        System.out.println("==============================================================");
        System.out.println("  TESTES - AdminUsuariosService (Gerenciamento de Usuários)");
        System.out.println("==============================================================");
    }

    @AfterAll
    static void fim() {
        System.out.println();
        System.out.println("==============================================================");
        System.out.println("  FIM DOS TESTES - AdminUsuariosService");
        System.out.println("==============================================================");
        System.out.println();
    }

    @BeforeEach
    void setUp(TestInfo info) {
        // Mapper real (não é mock), assim a conversão para DTO também é testada
        service = new AdminUsuariosService(usuariosRepository, new AdminUsuariosMapper());

        System.out.println();
        System.out.println(">> " + info.getDisplayName());
        System.out.println("--------------------------------------------------------------");
    }

    // ---------- helpers ----------

    private AdminUsuarios criarUsuario(Long id, String nome, String email, StatusAcesso status) {
        AdminUsuarios u = new AdminUsuarios();
        u.setId(id);
        u.setUsuarioUuid(UUID.randomUUID());
        u.setNome(nome);
        u.setEmail(email);
        u.setPerfil(PerfilUsuario.MENTORADO);
        u.setStatusAcesso(status);
        return u;
    }

    private AdminUsuarios maria(StatusAcesso status) {
        return criarUsuario(3L, "Maria Mentorada", "maria.dev@empresa.com", status);
    }

    private void assertStatusHttp(Throwable ex, HttpStatus esperado) {
        assertThat(ex).isInstanceOf(ResponseStatusException.class);
        ResponseStatusException rse = (ResponseStatusException) ex;
        System.out.println("  Erro retornado : " + rse.getStatusCode() + " - " + rse.getReason());
        assertThat(rse.getStatusCode()).isEqualTo(esperado);
        System.out.println("  [OK] retornou " + esperado.value() + " como esperado");
    }

    private void imprimirUsuario(AdminUsuariosDTO u) {
        System.out.printf("  [ID %d] %-18s | %-24s | %-9s | %s%n",
                u.getId(), u.getNome(), u.getEmail(), u.getStatusAcesso(),
                u.getMotivoSuspensao() == null ? "-" : "motivo: " + u.getMotivoSuspensao());
    }

    // ---------- listarUsuarios ----------

    @Test
    @DisplayName("Listar sem busca retorna todos os usuários")
    void listarSemBusca() {
        when(usuariosRepository.findAllByOrderByNomeAsc()).thenReturn(List.of(
                criarUsuario(1L, "Admin da Silva", "admin@plataforma.com", StatusAcesso.ATIVO),
                maria(StatusAcesso.ATIVO)));

        System.out.println("  Chamada        : GET /admin/users");
        List<AdminUsuariosDTO> resultado = service.listarUsuarios(null);

        System.out.println("  Usuários retornados (" + resultado.size() + "):");
        resultado.forEach(this::imprimirUsuario);

        assertThat(resultado).hasSize(2);
        assertThat(resultado.get(1).getNome()).isEqualTo("Maria Mentorada");
        assertThat(resultado.get(1).getUsuarioUuid()).isNotBlank();
        verify(usuariosRepository, never()).buscarPorNomeOuEmail(any());
        System.out.println("  [OK] listou todos sem usar a busca");
    }

    @Test
    @DisplayName("Listar com busca usa o termo sem espaços nas pontas")
    void listarComBusca() {
        when(usuariosRepository.buscarPorNomeOuEmail("maria"))
                .thenReturn(List.of(maria(StatusAcesso.ATIVO)));

        System.out.println("  Chamada        : GET /admin/users?search=\"  maria  \"");
        List<AdminUsuariosDTO> resultado = service.listarUsuarios("  maria  ");

        System.out.println("  Termo enviado ao banco: \"maria\"");
        System.out.println("  Usuários retornados (" + resultado.size() + "):");
        resultado.forEach(this::imprimirUsuario);

        assertThat(resultado).hasSize(1);
        assertThat(resultado.get(0).getEmail()).isEqualTo("maria.dev@empresa.com");
        verify(usuariosRepository, never()).findAllByOrderByNomeAsc();
        System.out.println("  [OK] buscou só pela Maria");
    }

    @Test
    @DisplayName("Listar com busca em branco se comporta como sem busca")
    void listarComBuscaEmBranco() {
        when(usuariosRepository.findAllByOrderByNomeAsc()).thenReturn(List.of());

        System.out.println("  Chamada        : GET /admin/users?search=\"   \"");
        List<AdminUsuariosDTO> resultado = service.listarUsuarios("   ");

        System.out.println("  Usuários retornados: " + resultado.size());
        assertThat(resultado).isEmpty();
        verify(usuariosRepository, never()).buscarPorNomeOuEmail(any());
        System.out.println("  [OK] busca em branco foi ignorada");
    }

    // ---------- suspenderUsuario ----------

    @Test
    @DisplayName("Suspender usuário ATIVO muda para SUSPENSO e grava o motivo")
    void suspenderComSucesso() {
        AdminUsuarios usuario = maria(StatusAcesso.ATIVO);
        when(usuariosRepository.findById(3L)).thenReturn(Optional.of(usuario));
        when(usuariosRepository.save(any(AdminUsuarios.class))).thenAnswer(inv -> inv.getArgument(0));

        System.out.println("  Chamada        : POST /admin/users/3/suspend");
        System.out.println("  Body           : { \"motivo\": \"  No-show repetido  \" }");
        System.out.println("  Antes          : " + usuario.getNome() + " -> " + usuario.getStatusAcesso());

        AdminUsuariosDTO resultado = service.suspenderUsuario(3L, "  No-show repetido  ");

        System.out.println("  Depois         : " + resultado.getNome() + " -> " + resultado.getStatusAcesso());
        System.out.println("  Motivo salvo   : \"" + resultado.getMotivoSuspensao() + "\"");

        assertThat(resultado.getStatusAcesso()).isEqualTo(StatusAcesso.SUSPENSO);
        assertThat(resultado.getMotivoSuspensao()).isEqualTo("No-show repetido");
        assertThat(usuario.getStatusAcesso()).isEqualTo(StatusAcesso.SUSPENSO);
        verify(usuariosRepository).save(usuario);
        System.out.println("  [OK] ATIVO -> SUSPENSO e motivo gravado");
    }

    @Test
    @DisplayName("Suspender sem motivo retorna 400 e nem consulta o banco")
    void suspenderSemMotivo() {
        System.out.println("  Chamada        : POST /admin/users/3/suspend");
        System.out.println("  Body           : { \"motivo\": \"   \" }");

        Throwable ex = catchThrowable(() -> service.suspenderUsuario(3L, "   "));

        assertStatusHttp(ex, HttpStatus.BAD_REQUEST);
        verify(usuariosRepository, never()).findById(any());
        verify(usuariosRepository, never()).save(any());
    }

    @Test
    @DisplayName("Suspender com motivo nulo retorna 400")
    void suspenderMotivoNulo() {
        System.out.println("  Chamada        : POST /admin/users/3/suspend");
        System.out.println("  Body           : (sem motivo)");

        Throwable ex = catchThrowable(() -> service.suspenderUsuario(3L, null));

        assertStatusHttp(ex, HttpStatus.BAD_REQUEST);
    }

    @Test
    @DisplayName("Suspender com motivo acima de 500 caracteres retorna 400")
    void suspenderMotivoGrande() {
        System.out.println("  Chamada        : POST /admin/users/3/suspend");
        System.out.println("  Body           : { \"motivo\": \"aaaa...\" } (501 caracteres)");

        Throwable ex = catchThrowable(() -> service.suspenderUsuario(3L, "a".repeat(501)));

        assertStatusHttp(ex, HttpStatus.BAD_REQUEST);
        verify(usuariosRepository, never()).save(any());
    }

    @Test
    @DisplayName("Suspender usuário inexistente retorna 404")
    void suspenderUsuarioInexistente() {
        when(usuariosRepository.findById(99L)).thenReturn(Optional.empty());

        System.out.println("  Chamada        : POST /admin/users/99/suspend");
        System.out.println("  Body           : { \"motivo\": \"No-show repetido\" }");

        Throwable ex = catchThrowable(() -> service.suspenderUsuario(99L, "No-show repetido"));

        assertStatusHttp(ex, HttpStatus.NOT_FOUND);
        verify(usuariosRepository, never()).save(any());
    }

    @Test
    @DisplayName("Suspender usuário já SUSPENSO retorna 400")
    void suspenderJaSuspenso() {
        when(usuariosRepository.findById(3L)).thenReturn(Optional.of(maria(StatusAcesso.SUSPENSO)));

        System.out.println("  Chamada        : POST /admin/users/3/suspend");
        System.out.println("  Status atual   : SUSPENSO");

        Throwable ex = catchThrowable(() -> service.suspenderUsuario(3L, "No-show repetido"));

        assertStatusHttp(ex, HttpStatus.BAD_REQUEST);
        verify(usuariosRepository, never()).save(any());
    }

    @Test
    @DisplayName("Suspender usuário INATIVO retorna 400")
    void suspenderInativo() {
        when(usuariosRepository.findById(3L)).thenReturn(Optional.of(maria(StatusAcesso.INATIVO)));

        System.out.println("  Chamada        : POST /admin/users/3/suspend");
        System.out.println("  Status atual   : INATIVO");

        Throwable ex = catchThrowable(() -> service.suspenderUsuario(3L, "No-show repetido"));

        assertStatusHttp(ex, HttpStatus.BAD_REQUEST);
        verify(usuariosRepository, never()).save(any());
    }
}