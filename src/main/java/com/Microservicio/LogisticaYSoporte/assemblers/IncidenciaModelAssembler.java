package com.Microservicio.LogisticaYSoporte.assemblers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.Microservicio.LogisticaYSoporte.controller.IncidenciaControllerV2;
import com.Microservicio.LogisticaYSoporte.model.Incidencia;

@Component
public class IncidenciaModelAssembler implements RepresentationModelAssembler<Incidencia, EntityModel<Incidencia>>{

    @Override
    public EntityModel<Incidencia> toModel(Incidencia incidencia) {
        return EntityModel.of(incidencia,
                linkTo(methodOn(IncidenciaControllerV2.class).getIncidenciaById(incidencia.getId())).withSelfRel(),
                linkTo(methodOn(IncidenciaControllerV2.class).getAllIncidencias()).withRel("incidencias"));
    }
    
}
