package iftm.microservice.admin_service.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import iftm.microservice.admin_service.dto.MetricasDashboardDTO;
import iftm.microservice.admin_service.dto.mapper.DashboardMapper;
import iftm.microservice.admin_service.repository.DashboardRepository;

@ExtendWith(MockitoExtension.class)
class DashboardServiceTest {

    @Mock
    private DashboardRepository repository;

    private final DashboardMapper mapper = Mappers.getMapper(DashboardMapper.class);

    @Test
    void deveColocarCadaContagemNoCampoCerto() {
        // Arrange: um número diferente para cada contagem
        when(repository.contarNovosCadastros(any(LocalDateTime.class))).thenReturn(11L);
        when(repository.contarMentoriasPendentes()).thenReturn(22L);
        when(repository.contarMentoresPendentes()).thenReturn(44L);
        when(repository.contarVouchersAtivos(any(LocalDateTime.class))).thenReturn(55L);

        DashboardService service = new DashboardService(repository, mapper);

        // Act
        MetricasDashboardDTO resultado = service.buscarMetricas();

        // Assert: cada número tem que estar no seu campo
        assertThat(resultado.getNovosCadastros()).isEqualTo(11);
        assertThat(resultado.getMentoriasPendentes()).isEqualTo(22);
        assertThat(resultado.getMentoriasRealizadasNoMes()).isEqualTo(5); // valor fixo provisório do service
        assertThat(resultado.getMentoresPendentes()).isEqualTo(44);
        assertThat(resultado.getVouchersAtivos()).isEqualTo(55);
        assertThat(resultado.getGeradoEm()).isNotNull();
    }
}