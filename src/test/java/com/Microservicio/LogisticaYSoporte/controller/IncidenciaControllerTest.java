package com.Microservicio.LogisticaYSoporte.controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.Microservicio.LogisticaYSoporte.model.Incidencia;
import com.Microservicio.LogisticaYSoporte.service.IncidenciaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;

@WebMvcTest(IncidenciaController.class) 
public class IncidenciaControllerTest {

     @Autowired
    private MockMvc mockMvc; 

    @MockBean
    private IncidenciaService incidenciaService;

    @Autowired
    private ObjectMapper objectMapper;


    private Incidencia incidencia;

      @BeforeEach
    void setup() {
      
        // Datos de prueba para la incidencia
        incidencia = new Incidencia();
        incidencia.setId(1);
        incidencia.setEncargado("Matias Dias");
        incidencia.setDescripcion("Caida de servidor");
        incidencia.setEstado("Abierta");
    }


    @Test
    public void getIncidenciaTest() throws Exception{
        when(incidenciaService.listarTodos()).thenReturn(Arrays.asList(incidencia));

        mockMvc.perform(get("/api/incidencia"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].encargado").value("Matias Dias"))
                .andExpect(jsonPath("$[0].descripcion").value("Caida de servidor"))
                .andExpect(jsonPath("$[0].estado").value("Abierta"));  
    }

    @Test
    public void createIncidenciaTest() throws Exception{
        when(incidenciaService.save(any(Incidencia.class))).thenReturn(incidencia);

        mockMvc.perform(post("/api/incidencia")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(incidencia)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.encargado").value("Matias Dias"))
                .andExpect(jsonPath("$.descripcion").value("Caida de servidor"))
                .andExpect(jsonPath("$.estado").value("Abierta"));  
    }

    @Test
    public void actualizarIncidenciaTest() throws Exception{
        when(incidenciaService.save(any(Incidencia.class))).thenReturn(incidencia);

        mockMvc.perform(put("/api/incidencia/1")
        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(incidencia)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.encargado").value("Matias Dias"))
                .andExpect(jsonPath("$.descripcion").value("Caida de servidor"))
                .andExpect(jsonPath("$.estado").value("Abierta"));  
    }

    @Test
    public void deleteIncidenciaTest() throws Exception{
        doNothing().when(incidenciaService).deleteById(1);

        mockMvc.perform(delete("/api/incidencia/1"))
                .andExpect(status().isOk());
        
        verify(incidenciaService, times(1)).deleteById(1);        
    }
    
}
