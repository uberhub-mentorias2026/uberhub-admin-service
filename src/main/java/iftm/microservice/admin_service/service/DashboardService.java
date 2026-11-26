package iftm.microservice.admin_service.service;
import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import iftm.microservice.admin_service.dto.MetricasDashboardDTO;
import iftm.microservice.admin_service.dto.mapper.DashboardMapper;
import iftm.microservice.admin_service.repository.DashboardRepository;
import lombok.AllArgsConstructor;

@Service 
@AllArgsConstructor
public class DashboardService {

    private final DashboardRepository repository;
    private final DashboardMapper mapper;
        
    public MetricasDashboardDTO buscarMetricas() {
    LocalDateTime agora = LocalDateTime.now();
    LocalDateTime desde = agora.minusDays(7);

    long novosCadastros = repository.contarNovosCadastros(desde);
    long mentoriasPendentes = repository.contarMentoriasPendentes();
    //falta decidir qual data define a mentoria realizada no mês
    long mentoriasRealizadasNoMes = 5;
    long mentoresPendentes = repository.contarMentoresPendentes();
    long vouchersAtivos = repository.contarVouchersAtivos(agora);


    return mapper.toDto(
        novosCadastros,
        mentoriasPendentes,
        mentoriasRealizadasNoMes,
        mentoresPendentes,
        vouchersAtivos);
}


}
    

