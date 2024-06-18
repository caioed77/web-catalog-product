package com.catalogodeproduto.apicatalogo.services;


import com.catalogodeproduto.apicatalogo.config.JwtServiceConfig;
import com.catalogodeproduto.apicatalogo.domain.dto.AuthenticationDTO;
import com.catalogodeproduto.apicatalogo.domain.dto.RegisterDTO;
import com.catalogodeproduto.apicatalogo.domain.dto.UsuarioDTO;
import com.catalogodeproduto.apicatalogo.entities.TokenEntity;
import com.catalogodeproduto.apicatalogo.entities.UsuarioEntity;
import com.catalogodeproduto.apicatalogo.entities.enumEntities.TokenType;
import com.catalogodeproduto.apicatalogo.exceptions.RegrasDeNegocioException;
import com.catalogodeproduto.apicatalogo.exceptions.UsuarioNaoAutorizadoException;
import com.catalogodeproduto.apicatalogo.repositories.TokenRepository;
import com.catalogodeproduto.apicatalogo.repositories.UsuarioRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

      private final UsuarioRepository repository;
      private final TokenRepository tokenRepository;
      private final PasswordEncoder passwordEncoder;
      private final JwtServiceConfig jwtService;
      private final AuthenticationManager authenticationManager;

      public AuthenticationDTO register(RegisterDTO request) {
            var user = UsuarioEntity.builder()
                    .email(request.email())
                    .senha(passwordEncoder.encode(request.senha()))
                    .role(request.role())
                    .build();

            var savedUser = repository.save(user);
            var jwtToken = jwtService.generateToken(user);
            var refreshToken = jwtService.generateRefreshToken(user);

            saveUserToken(savedUser, jwtToken);
            return new AuthenticationDTO(jwtToken, refreshToken, null);
      }

      public AuthenticationDTO authenticate(UsuarioDTO request) throws UsuarioNaoAutorizadoException {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.email(),
                            request.senha()
                    )
            );

            var user = repository.findByEmail(request.email())
                    .orElseThrow(() -> new RegrasDeNegocioException("Usuario não foi encontrado com email informado"));

            var jwtToken = jwtService.generateToken(user);
            var refreshToken = jwtService.generateRefreshToken(user);
            var usuario = new UsuarioDTO(user.getEmail(), null, user.getRole());

            if (jwtService.isTokenValid(refreshToken, user)) {
                  var accessToken = jwtService.generateToken(user);
                  saveUserToken(user, accessToken);
                  return new AuthenticationDTO(accessToken, refreshToken, usuario);
            } else {
                  revokeAllUserTokens(user);
                  saveUserToken(user, jwtToken);
                  throw new UsuarioNaoAutorizadoException("Usuario não autorizado");
            }
      }

      private void saveUserToken(UsuarioEntity user, String jwtToken) {
            var token = TokenEntity.builder()
                    .user(user)
                    .token(jwtToken)
                    .tokenType(TokenType.BEARER)
                    .expired(false)
                    .revoked(false)
                    .build();
            tokenRepository.save(token);
      }

      private void revokeAllUserTokens(UsuarioEntity user) {
            var validUserTokens = tokenRepository.findAllValidTokenByUser(Math.toIntExact(user.getId()));
            if (validUserTokens.isEmpty())
                  return;
            validUserTokens.forEach(token -> {
                  token.setExpired(true);
                  token.setRevoked(true);
            });
            tokenRepository.saveAll(validUserTokens);
      }

      public void refreshToken(
              HttpServletRequest request,
              HttpServletResponse response
      ) throws IOException {
            final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
            final String refreshToken;
            final String userEmail;
            if (authHeader == null ||!authHeader.startsWith("Bearer ")) {
                  return;
            }
            refreshToken = authHeader.substring(7);
            userEmail = jwtService.extractUsername(refreshToken);
            if (userEmail != null) {
                  var user = this.repository.findByEmail(userEmail).orElseThrow(() -> new RegrasDeNegocioException("Usuario não encontrado com email informado"));

                  if (jwtService.isTokenValid(refreshToken, user)) {
                        var accessToken = jwtService.generateToken(user);
                        revokeAllUserTokens(user);
                        saveUserToken(user, accessToken);
                        var authResponse = new AuthenticationDTO(accessToken, refreshToken, null);
                        new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
                  }
            }
      }
}
