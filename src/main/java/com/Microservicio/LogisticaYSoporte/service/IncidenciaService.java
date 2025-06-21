package com.Microservicio.LogisticaYSoporte.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Microservicio.LogisticaYSoporte.model.Incidencia;
import com.Microservicio.LogisticaYSoporte.repository.IncidenciaRepository;

@Service
public class IncidenciaService {
    @Autowired
    private IncidenciaRepository incidenciaRepository;

    public List<Incidencia> listarTodos(){
        return incidenciaRepository.findAll();
    }

    public Incidencia save(Incidencia incidencia){
        return incidenciaRepository.save(incidencia);       
    }

    public Incidencia actualizarIncidencia(int id, Incidencia incidencia){
        Incidencia buscado = incidenciaRepository.findById(id);
        buscado.setDescripcion(incidencia.getDescripcion());
        buscado.setEncargado(incidencia.getEncargado());
        buscado.setEstado(incidencia.getEstado());
        return incidenciaRepository.save(incidencia);
    }

    public Incidencia findById(int id){
        return incidenciaRepository.findById(id);
    }
    
    public void deleteById(int id){
        incidenciaRepository.deleteById(id);
    }
    
}
