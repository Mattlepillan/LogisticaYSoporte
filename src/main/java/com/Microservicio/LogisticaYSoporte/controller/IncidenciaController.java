package com.Microservicio.LogisticaYSoporte.controller;

import com.Microservicio.LogisticaYSoporte.model.Incidencia;
import com.Microservicio.LogisticaYSoporte.service.IncidenciaService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;


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


@RestController
@RequestMapping("/api/incidencia")
@Tag(name = "Incidencias", description = "Operaciones relacionadas con las incidencias")
public class IncidenciaController {
    @Autowired
    private IncidenciaService incidenciaService;

    @GetMapping
    @Operation(summary = "Obtener todas las incidencias", description = "Obtiene una lista de todas las incidencias")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operación exitosa",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Incidencia.class))),
            @ApiResponse(responseCode = "404", description = "No hay contenido para mostrar")                
    })

    public ResponseEntity<List<Incidencia>> getIncidencia(){
        List<Incidencia> incidencias = incidenciaService.listarTodos();
        if (incidencias.isEmpty())
        {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(incidencias,HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Incidencia> BuscarPorId(@PathVariable int id) {
        Incidencia incidencia = incidenciaService.findById(id);

        if (incidencia == null)
        {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(incidencia,HttpStatus.OK);
    }
    

    @PostMapping
    @Operation(summary = "Crear una nueva incidencia", description = "Crea una nueva incidencia")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Carrera creada exitosamente",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Incidencia.class)))
    })

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
    @Operation(summary = "Actualizar una incidencia", description = "Actualiza una incidencia existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Carrera actualizada exitosamente",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Incidencia.class))),
            @ApiResponse(responseCode = "404", description = "Carrera no encontrada")
    })

    public ResponseEntity<Incidencia> putIncidencia(@PathVariable int id, @RequestBody Incidencia incidencia){
        if(incidencia == null)
        {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        else
        {
            return new ResponseEntity<>(incidenciaService.save(incidencia),HttpStatus.OK);
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar una incidencia", description = "Elimina una incidencia por su código")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Carrera eliminada exitosamente"),
            @ApiResponse(responseCode = "404", description = "Carrera no encontrada")
    })

    public ResponseEntity<Incidencia> deleteIncidenciaById(@PathVariable int id, Incidencia incidencia){
       
        incidenciaService.deleteById(id);
        return new ResponseEntity<>( HttpStatus.OK);
    }
    
}
