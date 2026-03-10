package com.giselle.forohub.controller;

import com.giselle.forohub.domain.user.Usuario;
import com.giselle.forohub.domain.user.UsuarioRepository;
import com.giselle.forohub.domain.user.dto.DatosAutenticacionUsuario;
import com.giselle.forohub.domain.user.dto.DatosJWTToken;
import com.giselle.forohub.infra.security.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/login")
public class AutenticacionController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private TokenService tokenService;

    @PostMapping
    public ResponseEntity login(@RequestBody DatosAutenticacionUsuario datos){

        Usuario usuario = usuarioRepository.findByEmail(datos.email())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        if(!usuario.getPassword().equals(datos.password())){
            throw new RuntimeException("Credenciales incorrectas");
        }

        String token = tokenService.generarToken(usuario);

        return ResponseEntity.ok(new DatosJWTToken(token));
    }
}