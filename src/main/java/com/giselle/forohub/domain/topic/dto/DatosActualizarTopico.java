package com.giselle.forohub.domain.topic.dto;

import jakarta.validation.constraints.NotNull;

public record DatosActualizarTopico(

        @NotNull
        Long id,

        String titulo,

        String mensaje

) {
}