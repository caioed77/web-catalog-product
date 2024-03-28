package com.catalogodeproduto.apicatalogo.services;

import com.catalogodeproduto.apicatalogo.dto.UsuarioDTO;
import com.catalogodeproduto.apicatalogo.entities.UsuarioEntity;
import com.catalogodeproduto.apicatalogo.exceptions.RegrasDeNegocioException;
import com.catalogodeproduto.apicatalogo.repositories.UsuarioRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final ValidarEmail validarEmail;

    public UsuarioService(UsuarioRepository usuarioRepository, ValidarEmail validarEmail) {
        this.usuarioRepository = usuarioRepository;
        this.validarEmail = validarEmail;
    }

    public Optional<UsuarioEntity> loadUserByUsername(String userName) {
       var validarUser = Optional.of(usuarioRepository.findByEmail(userName)).orElseThrow(() -> new RegrasDeNegocioException("Usuario não encontrado"));

        if (validarUser != null) {
            return Optional.ofNullable(Optional.of(validarUser).orElseThrow(
                    () -> new RegrasDeNegocioException("Não foi possivel encontrar o email:" + validarUser.getEmail())));
        }
        return Optional.empty();
    }

    @Transactional
    public void criarUsuario(UsuarioDTO usuario) {
        var usuarioValido = validarEmail.validadorEmail(usuario.email());
        BCryptPasswordEncoder criptografar = new BCryptPasswordEncoder();
        var novoUsuario = new UsuarioEntity();

        if (usuarioValido) {
            novoUsuario.setEmail(usuario.email());
            var senhaCriptografada = criptografar.encode(usuario.senha());
            novoUsuario.setSenha(senhaCriptografada);
            novoUsuario.setPermissaoAdmin(usuario.permissao());
            usuarioRepository.save(novoUsuario);
        } else {
            throw new RegrasDeNegocioException("Email invalido");
        }

    }
}
