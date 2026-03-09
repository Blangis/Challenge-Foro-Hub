package com.giselle.forohub.controller;

import com.giselle.forohub.domain.course.Curso;
import com.giselle.forohub.domain.course.CursoRepository;
import com.giselle.forohub.domain.topic.*;
import com.giselle.forohub.domain.topic.dto.DatosRegistroTopico;
import com.giselle.forohub.domain.user.Usuario;
import com.giselle.forohub.domain.user.UsuarioRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/topicos")
public class TopicoController {

    @Autowired
    private TopicoRepository topicoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private CursoRepository cursoRepository;


    @PostMapping
    public void registrarTopico(@RequestBody @Valid DatosRegistroTopico datos) {

        Usuario autor = usuarioRepository.getReferenceById(datos.autorId());
        Curso curso = cursoRepository.getReferenceById(datos.cursoId());

        Topico topico = new Topico(
                null,
                datos.titulo(),
                datos.mensaje(),
                LocalDateTime.now(),
                StatusTopico.ABIERTO,
                autor,
                curso
        );

        topicoRepository.save(topico);
    }

}