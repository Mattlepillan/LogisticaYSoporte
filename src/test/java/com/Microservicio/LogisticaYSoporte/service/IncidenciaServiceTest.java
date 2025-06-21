package com.Microservicio.LogisticaYSoporte.service;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.Microservicio.LogisticaYSoporte.model.Incidencia;
import com.Microservicio.LogisticaYSoporte.repository.IncidenciaRepository;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.List;

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
    void saveTest(){
        Incidencia incidencia = new Incidencia(1, "Matias Dias", "Caida de servidor", "Abierta");
        Incidencia incidenciaGuardada = new Incidencia(1, "Matias Dias", "aidad de servidor", "Abierta");
        when(incidenciaRepository.save(incidencia)).thenReturn(incidenciaGuardada);

        Incidencia resultado = incidenciaService.save(incidencia);
        assertThat(resultado.getId()).isEqualTo(1);
        verify(incidenciaRepository).save(incidencia);
    }

    @Test
    void listarTodosTest(){
        Incidencia i1 = new Incidencia(1, "Matias Dias", "Caida de servidor", "Abierta");
        Incidencia i2 = new Incidencia(2, "Leonardo Tapia", "Fallo en el sistema de alumnos", "Cerrada");
        when(incidenciaRepository.findAll()).thenReturn(Arrays.asList(i1, i2));

        List<Incidencia> resultado = incidenciaService.listarTodos();
        assertThat(resultado).hasSize(2).contains(i1, i2);
        verify(incidenciaRepository).findAll();
    }
    
    @Test
    void findByIdTest(){
        Incidencia i1 = new Incidencia(1, "Matias Dias", "Caida de servidor", "Abierta");
        when(incidenciaRepository.findById(i1.getId())).thenReturn(i1);

        Incidencia resultado = incidenciaService.findById(i1.getId());
        assertThat(resultado.getId()).isEqualTo(1);
        assertThat(resultado.getEncargado()).isEqualTo("Matias Dias");
        assertThat(resultado.getDescripcion()).isEqualTo("Caida de servidor");
        assertThat(resultado.getEstado()).isEqualTo("Abierta");
        verify(incidenciaRepository).findById(1); 
    }

    @Test
    void actualizarIncidenciaTest(){
        Incidencia i1 = new Incidencia(1, "Matias Dias", "Caida de servidor", "Abierta");
        Incidencia i2 = new Incidencia(1, "Leonardo Tapia", "Fallo en el sistema de alumosn", "Cerrada");
        when(incidenciaRepository.findById(i1.getId())).thenReturn(i1);
        when(incidenciaRepository.save(Mockito.any(Incidencia.class))).thenReturn(i2);

        Incidencia resultado = incidenciaService.actualizarIncidencia(1, i2);
        assertThat(resultado).isNotNull();
        assertThat(resultado.getId()).isEqualTo(1);
        assertThat(resultado.getEncargado()).isEqualTo("Leonardo Tapia");
        assertThat(resultado.getDescripcion()).isEqualTo("Fallo en el sistema de alumosn");
        assertThat(resultado.getEstado()).isEqualTo("Cerrada");
    }   

    @Test
    void deleteTest(){
        int id = 1;
        doNothing().when(incidenciaRepository).deleteById(id);
        incidenciaService.deleteById(id);
        verify(incidenciaRepository, times(1)).deleteById(id);
    }
    
}
