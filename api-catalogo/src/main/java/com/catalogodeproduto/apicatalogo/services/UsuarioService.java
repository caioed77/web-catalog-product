package com.catalogodeproduto.apicatalogo.services;

import com.catalogodeproduto.apicatalogo.domain.dto.ChangePasswordDTO;
import com.catalogodeproduto.apicatalogo.entities.UsuarioEntity;
import com.catalogodeproduto.apicatalogo.repositories.UsuarioRepository;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final ValidarEmail validarEmail;

    private final PasswordEncoder passwordEncoder;

    public UsuarioService(UsuarioRepository usuarioRepository, ValidarEmail validarEmail, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.validarEmail = validarEmail;
        this.passwordEncoder = passwordEncoder;
    }


    public void changePassword(ChangePasswordDTO request, Principal connectedUser) {

        var user = (UsuarioEntity) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();
        if (!passwordEncoder.matches(request.currentPassword(), user.getPassword())) {
            throw new IllegalStateException("Wrong password");
        }

        if (!request.newPassword().equals(request.confirmationPassword())) {
            throw new IllegalStateException("Password are not the same");
        }

        user.setSenha(passwordEncoder.encode(request.newPassword()));

        usuarioRepository.save(user);
    }
}
