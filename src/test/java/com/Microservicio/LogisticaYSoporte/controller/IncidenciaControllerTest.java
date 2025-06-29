package com.Microservicio.LogisticaYSoporte.controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.Microservicio.LogisticaYSoporte.model.Incidencia;
import com.Microservicio.LogisticaYSoporte.service.IncidenciaService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;

@WebMvcTest(IncidenciaController.class) 
public class IncidenciaControllerTest {

     @Autowired
    private MockMvc mockMvc; 

    @MockBean
    private IncidenciaService incidenciaService;

    @Autowired
    private ObjectMapper objectMapper;

    

    @Test
    public void createIncidenciaTest() throws Exception{
        Incidencia nueva = new Incidencia(0, "Matias Dias", "Caida de servidor", "Abierta");
        Incidencia guardada = new Incidencia(1, "Matias Dias", "Caida de servidor", "Abierta");
        when(incidenciaService.save(any(Incidencia.class))).thenReturn(guardada);

        mockMvc.perform(post("/api/incidencia")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(nueva)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.encargado").value("Matias Dias"))
                .andExpect(jsonPath("$.descripcion").value("Caida de servidor"))
                .andExpect(jsonPath("$.estado").value("Abierta")); 
                    
    }

    @Test
    void createIncidenciaExistente() throws Exception {
        Incidencia existente = new Incidencia(1, "Ana", "Desc", "Abierta");
        when(incidenciaService.findById(1)).thenReturn(existente);

        mockMvc.perform(post("/api/incidencia")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(existente)))
                .andExpect(status().isNotAcceptable());

        verify(incidenciaService, never()).save(any());
    }

    @Test
    public void getIncidenciaTest() throws Exception{
        Incidencia i1 = new Incidencia(1, "Matias Dias", "Caida de servidor", "Abierta");
        Incidencia i2 = new Incidencia(2, "Leonardo Tapia", "Fallo en el sistema de alumnos", "Cerrada");
        when(incidenciaService.listarTodos()).thenReturn(Arrays.asList(i1, i2));

        mockMvc.perform(get("/api/incidencia"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].encargado").value("Matias Dias"))
                .andExpect(jsonPath("$[1].descripcion").value("Fallo en el sistema de alumnos"))
                .andExpect(jsonPath("$[1].estado").value("Cerrada"));  

    }

    @Test
    void getIncidenciaSinContenido() throws Exception {
        when(incidenciaService.listarTodos()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/incidencia"))
            .andExpect(status().isNoContent());
    }

    @Test
    public void getIncidenciaByIdTest() throws Exception{
        Incidencia incidencia = new Incidencia(1, "Matias Dias", "Caida de servidor", "Abierta");
        when(incidenciaService.findById(incidencia.getId())).thenReturn(incidencia);

        mockMvc.perform(get("/api/incidencia/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.encargado").value("Matias Dias"))
                .andExpect(jsonPath("$.descripcion").value("Caida de servidor"))
                .andExpect(jsonPath("$.estado").value("Abierta")); 
    }

    @Test
    void getIncidenciaByIdInexistente() throws Exception {
        when(incidenciaService.findById(1)).thenReturn(null);

        mockMvc.perform(get("/api/incidencia/1"))
            .andExpect(status().isNoContent());
    }



    @Test
    public void actualizarIncidenciaTest() throws Exception{
        Incidencia incidencia = new Incidencia(1, "Matias Dias", "Caida de servidor", "Abierta");
        Incidencia incidenciaAct = new Incidencia(1, "Leonardo Tapia", "Fallo en el sistema de alumnos", "Cerrada");
        when(incidenciaService.findById(incidencia.getId())).thenReturn(incidencia);
        when(incidenciaService.save(any(Incidencia.class))).thenReturn(incidenciaAct);

        mockMvc.perform(put("/api/incidencia/1")
        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(incidencia)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.encargado").value("Leonardo Tapia"))
                .andExpect(jsonPath("$.descripcion").value("Fallo en el sistema de alumnos"))
                .andExpect(jsonPath("$.estado").value("Cerrada"));  
    }



    @Test
    public void deleteIncidenciaTest() throws Exception{
        int id = 1;
        doNothing().when(incidenciaService).deleteById(id);

        mockMvc.perform(delete("/api/incidencia/1"))
                .andExpect(status().isOk());
        
        verify(incidenciaService, times(1)).deleteById(id);        
    }
    
}
