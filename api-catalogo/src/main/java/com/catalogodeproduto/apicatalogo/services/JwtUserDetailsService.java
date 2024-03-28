package com.catalogodeproduto.apicatalogo.services;

import com.catalogodeproduto.apicatalogo.exceptions.RegrasDeNegocioException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;

public class JwtUserDetailsService implements UserDetailsService {

    private final UsuarioService usuarioService;

    public JwtUserDetailsService(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
       var usuario = usuarioService.loadUserByUsername(username).orElseThrow(() -> new RegrasDeNegocioException("Usuario não encontrado"));

       return new User(usuario.getEmail(), usuario.getSenha(), new ArrayList<>());
    }
}
