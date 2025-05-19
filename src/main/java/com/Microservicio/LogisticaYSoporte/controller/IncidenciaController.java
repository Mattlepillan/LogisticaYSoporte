package com.Microservicio.LogisticaYSoporte.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Microservicio.LogisticaYSoporte.model.Incidencia;
import com.Microservicio.LogisticaYSoporte.service.IncidenciaService;

@RestController
@RequestMapping("/api/incidencia")
public class IncidenciaController {
    @Autowired
    private IncidenciaService incidenciaService;

    @GetMapping
    public ResponseEntity<List<Incidencia>> getIncidencia(){
        List<Incidencia> incidencias = incidenciaService.listarTodos();
        if (incidencias.isEmpty())
        {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(incidencias,HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Incidencia> postIncidencia(@RequestBody Incidencia incidencia){
        Incidencia buscado = incidenciaService.findById(incidencia.getId());
        if(buscado == null)
        {
            return new ResponseEntity<>(incidenciaService.save(incidencia), HttpStatus.CREATED);
        }
        else
        {
            
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }


    }

    @PutMapping("/{id}")
    public ResponseEntity<Incidencia> putIncidencia(@PathVariable int id, @RequestBody Incidencia incidencia){
        Incidencia buscado = incidenciaService.findById(incidencia.getId());
        if(buscado == null)
        {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        else
        {
            buscado.setDescripcion(incidencia.getDescripcion());
            buscado.setEstado(incidencia.getEstado());
            return new ResponseEntity<>(incidenciaService.save(incidencia),HttpStatus.OK);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Incidencia> deleteCurso(@PathVariable int id, @RequestBody Incidencia incidencia){
        Incidencia buscado = incidenciaService.findById(incidencia.getId());
        if(buscado == null)
        {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        else
        {
            incidenciaService.delete(incidencia);
            return new ResponseEntity<>( HttpStatus.OK);
        }
    }
    
}
