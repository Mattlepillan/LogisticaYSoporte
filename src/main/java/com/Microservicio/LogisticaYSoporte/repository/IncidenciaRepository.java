package com.Microservicio.LogisticaYSoporte.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Microservicio.LogisticaYSoporte.model.Incidencia;

@Repository
public interface IncidenciaRepository extends JpaRepository<Incidencia, Integer>{
    List<Incidencia> findAll();
    
    @SuppressWarnings("unchecked")
    Incidencia save(Incidencia incidencia);

    Incidencia findById(int id);

    Incidencia getReferenceById(Integer id); 

    void deleteById(Incidencia incidencia);
}
