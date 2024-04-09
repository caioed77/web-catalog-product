package com.catalogodeproduto.apicatalogo.controllers;


import com.catalogodeproduto.apicatalogo.dto.AuthenticationDTO;
import com.catalogodeproduto.apicatalogo.dto.RegisterDTO;
import com.catalogodeproduto.apicatalogo.dto.UsuarioDTO;
import com.catalogodeproduto.apicatalogo.exceptions.UsuarioNaoAutorizadoException;
import com.catalogodeproduto.apicatalogo.services.AuthenticationService;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

      private final AuthenticationService service;

      @PostMapping("/register")
      public ResponseEntity<AuthenticationDTO> register(
              @RequestBody RegisterDTO request
      ) {
            return ResponseEntity.ok(service.register(request));
      }
      @PostMapping("/authenticate")
      public ResponseEntity<AuthenticationDTO> authenticate(
              @RequestBody UsuarioDTO request
      ) throws UsuarioNaoAutorizadoException {
            return ResponseEntity.ok(service.authenticate(request));
      }

      @PostMapping("/refresh-token")
      public void refreshToken(
              HttpServletRequest request,
              HttpServletResponse response
      ) throws IOException {
            service.refreshToken(request, response);
      }


}
