package com.catalogodeproduto.apicatalogo.controllers;

import com.catalogodeproduto.apicatalogo.dto.UsuarioDTO;
import com.catalogodeproduto.apicatalogo.services.UsuarioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/usuario")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService){
        this.usuarioService = usuarioService;
    }

    @PostMapping(value = "/cadastro")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Void> createUsuario(@RequestBody UsuarioDTO usuarioDTO) {
        usuarioService.criarUsuario(usuarioDTO);
        return ResponseEntity.ok().build();
    }
}
