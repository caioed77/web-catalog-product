package com.catalogodeproduto.apicatalogo.controllers;

import com.catalogodeproduto.apicatalogo.services.UsuarioService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/usuario")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService){
        this.usuarioService = usuarioService;
    }
}
