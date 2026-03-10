package com.giselle.forohub.controller;

import com.giselle.forohub.domain.course.Curso;
import com.giselle.forohub.domain.course.CursoRepository;
import com.giselle.forohub.domain.topic.*;
import com.giselle.forohub.domain.topic.dto.DatosActualizarTopico;
import com.giselle.forohub.domain.topic.dto.DatosListadoTopico;
import com.giselle.forohub.domain.topic.dto.DatosRegistroTopico;
import com.giselle.forohub.domain.user.Usuario;
import com.giselle.forohub.domain.user.UsuarioRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
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
    public ResponseEntity registrarTopico(
            @RequestBody @Valid DatosRegistroTopico datos,
            UriComponentsBuilder uriBuilder) {

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

        URI url = uriBuilder.path("/topicos/{id}")
                .buildAndExpand(topico.getId())
                .toUri();

        return ResponseEntity.created(url).build();
    }

    @GetMapping
    public ResponseEntity<Page<DatosListadoTopico>> listarTopicos(
            @PageableDefault(size = 10, sort = "fechaCreacion") Pageable paginacion) {

        Page<DatosListadoTopico> page = topicoRepository
                .findAll(paginacion)
                .map(DatosListadoTopico::new);

        return ResponseEntity.ok(page);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DatosListadoTopico> detallarTopico(@PathVariable Long id) {

        Topico topico = topicoRepository.getReferenceById(id);

        return ResponseEntity.ok(new DatosListadoTopico(topico));
    }

    @PutMapping
    public ResponseEntity actualizarTopico(
            @RequestBody @Valid DatosActualizarTopico datos) {

        Topico topico = topicoRepository.getReferenceById(datos.id());

        if (datos.titulo() != null) {
            topico.setTitulo(datos.titulo());
        }

        if (datos.mensaje() != null) {
            topico.setMensaje(datos.mensaje());
        }

        return ResponseEntity.ok(new DatosListadoTopico(topico));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity eliminarTopico(@PathVariable Long id) {

        topicoRepository.deleteById(id);

        return ResponseEntity.noContent().build();
    }

}