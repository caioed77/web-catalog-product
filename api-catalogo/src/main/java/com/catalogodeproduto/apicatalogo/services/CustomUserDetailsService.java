package com.catalogodeproduto.apicatalogo.services;

import com.catalogodeproduto.apicatalogo.exceptions.RegrasDeNegocioException;
import com.catalogodeproduto.apicatalogo.repositories.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

      private final UsuarioRepository userRepository;
      @Override
      public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

            var users = Optional.ofNullable(userRepository.findByEmail(username)
                    .orElseThrow(() -> new RegrasDeNegocioException("Não foi encontrado usuario com esse email")));

            if (users.isEmpty()) return null;
            List<String> roles = new ArrayList<>();
            roles.add("USER");
            return User.builder().username(users.get().getEmail()).password(users.get().getSenha()).roles(roles.toArray(new String[0])).build();
      }

      public String userId(String username) {
            var user = Optional.ofNullable(userRepository.findByEmail(username))
                    .orElseThrow(() -> new RegrasDeNegocioException("Não foi encontrado usuario com esse email"));

            return user.map(value -> value.getId().toString()).orElse(null);
      }

}
