package iftm.microservice.admin_service.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import iftm.microservice.admin_service.dto.MetricasDashboardDTO;
import iftm.microservice.admin_service.service.DashboardService;
import lombok.AllArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;


@AllArgsConstructor 
@RestController 
@RequestMapping("/admin")
public class DashboardController {

    private final DashboardService service;

    @GetMapping("/dashboard")
    public ResponseEntity<MetricasDashboardDTO> buscarMetricas() {
        // return ResponseEntity.ok(service.buscarMetricas());
        MetricasDashboardDTO metricas = service.buscarMetricas();
        return ResponseEntity.ok(metricas);
    }
    
    

}
