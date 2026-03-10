package com.giselle.forohub.domain.topic.dto;

import com.giselle.forohub.domain.topic.StatusTopico;
import com.giselle.forohub.domain.topic.Topico;

import java.time.LocalDateTime;

public record DatosListadoTopico(

        Long id,
        String titulo,
        String mensaje,
        LocalDateTime fechaCreacion,
        StatusTopico status

) {

    public DatosListadoTopico(Topico topico) {
        this(
                topico.getId(),
                topico.getTitulo(),
                topico.getMensaje(),
                topico.getFechaCreacion(),
                topico.getStatus()
        );
    }

}