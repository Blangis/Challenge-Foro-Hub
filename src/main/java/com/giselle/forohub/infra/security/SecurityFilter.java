package com.giselle.forohub.infra.security;

import com.giselle.forohub.domain.user.Usuario;
import com.giselle.forohub.domain.user.UsuarioRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        String header = request.getHeader("Authorization");

        if(header != null && header.startsWith("Bearer ")){

            String token = header.replace("Bearer ", "");

            String email = tokenService.getSubject(token);

            if(email != null){

                Usuario usuario = usuarioRepository.findByEmail(email).orElse(null);

                if(usuario != null){

                    var authentication =
                            new UsernamePasswordAuthenticationToken(usuario, null, null);

                    SecurityContextHolder.getContext().setAuthentication(authentication);

                }

            }

        }

        filterChain.doFilter(request,response);
    }
}