package iftm.microservice.admin_service.service;

import java.time.Instant;

import org.springframework.stereotype.Service;

import iftm.microservice.admin_service.dto.MetricasDashboardDTO;

@Service 
//@AllArgsConstructor
public class DashboardService {

    public MetricasDashboardDTO buscarMetricas(){
        //valores provisórios, trocar depois pelos valores vindos do repository
       return MetricasDashboardDTO.builder()
                .novosCadastros(18)
                .mentoriasPendentes(10)
                .mentoriasRealizadasNoMes(5)
                .mentoresPendentes(7)
                .vouchersStatus(8)
                .geradoEm(Instant.now())
                .build();
    }

    // builder protege contra trocar campos
}
