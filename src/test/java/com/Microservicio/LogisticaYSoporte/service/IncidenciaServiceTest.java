package com.Microservicio.LogisticaYSoporte.service;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.Microservicio.LogisticaYSoporte.model.Incidencia;
import com.Microservicio.LogisticaYSoporte.repository.IncidenciaRepository;
import static org.assertj.core.api.Assertions.assertThat;

public class IncidenciaServiceTest {

    @Mock
    private IncidenciaRepository incidenciaRepository;

    @InjectMocks
    private IncidenciaService incidenciaService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSave(){
        Incidencia incidencia = new Incidencia(1, "Matias Dias", "Caida de servidor", "Abierta");
        Incidencia incidenciaGuardada = new Incidencia(1, "Matias Dias", "aidad de servidor", "Abierta");
        when(incidenciaRepository.save(incidencia)).thenReturn(incidenciaGuardada);

        Incidencia resultado = incidenciaService.save(incidencia);
        assertThat(resultado.getId()).isEqualTo(1);
        verify(incidenciaRepository).save(incidencia);
    }
    
}
