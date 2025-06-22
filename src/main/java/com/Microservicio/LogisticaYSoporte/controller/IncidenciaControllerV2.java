package com.Microservicio.LogisticaYSoporte.controller;

import java.util.stream.Collectors;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.Microservicio.LogisticaYSoporte.assemblers.IncidenciaModelAssembler;
import com.Microservicio.LogisticaYSoporte.model.Incidencia;
import com.Microservicio.LogisticaYSoporte.service.IncidenciaService;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;


@RestController
@RequestMapping("/api/v2/incidencia")
public class IncidenciaControllerV2 {

      @Autowired
    private IncidenciaService incidenciaService;

    @Autowired
    private IncidenciaModelAssembler assembler;

     @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public CollectionModel<EntityModel<Incidencia>> getAllIncidencias() {
        List<EntityModel<Incidencia>> incidencias = incidenciaService.listarTodos().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(incidencias,
                linkTo(methodOn(IncidenciaControllerV2.class).getAllIncidencias()).withSelfRel());
    }

    @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public EntityModel<Incidencia> getIncidenciaById(@PathVariable int id) {
        Incidencia incidencia = incidenciaService.findById(id);
        return assembler.toModel(incidencia);
    }

    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Incidencia>> createCarrera(@RequestBody Incidencia incidencia) {
        Incidencia newIncidencia = incidenciaService.save(incidencia);
        return ResponseEntity
                .created(linkTo(methodOn(IncidenciaControllerV2.class).getIncidenciaById(newIncidencia.getId())).toUri())
                .body(assembler.toModel(newIncidencia));
    }

    @PutMapping(value = "/{codigo}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Incidencia>> updateCarrera(@PathVariable int id, @RequestBody Incidencia incidencia) {
        incidencia.setId(id);
        Incidencia updatedIncidencia = incidenciaService.save(incidencia);
        return ResponseEntity
                .ok(assembler.toModel(updatedIncidencia));
    }

    @DeleteMapping(value = "/{codigo}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<?> deleteIncidencia(@PathVariable int id) {
        incidenciaService.deleteById(id);;
        return ResponseEntity.noContent().build();
    }

    
}
