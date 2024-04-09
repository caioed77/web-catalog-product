package com.catalogodeproduto.apicatalogo.config.jwtMiddleware;

import com.catalogodeproduto.apicatalogo.entities.UsuarioEntity;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

public class ApplicationAuditAware implements AuditorAware<Integer> {
      @Override
      public Optional<Integer> getCurrentAuditor() {
            Authentication authentication =
                    SecurityContextHolder
                            .getContext()
                            .getAuthentication();
            if (authentication == null ||
                    !authentication.isAuthenticated() ||
                    authentication instanceof AnonymousAuthenticationToken
            ) {
                  return Optional.empty();
            }

            UsuarioEntity userPrincipal = (UsuarioEntity) authentication.getPrincipal();
            return Optional.of(Math.toIntExact(userPrincipal.getId()));
      }
}
