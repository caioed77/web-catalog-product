package com.catalogodeproduto.apicatalogo.config.jwtMiddleware;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import com.catalogodeproduto.apicatalogo.config.JwtServiceConfig;
import com.catalogodeproduto.apicatalogo.repositories.TokenRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

      private final JwtServiceConfig jwtService;
      private final UserDetailsService userDetailsService;
      private final TokenRepository tokenRepository;

      private List<String> whiteListUrls;

      public void setWhiteListUrls(String[] whiteListUrls) {
            this.whiteListUrls = Arrays.asList(whiteListUrls);
      }

      @Override
      protected void doFilterInternal(
              @NonNull HttpServletRequest request,
              @NonNull HttpServletResponse response,
              @NonNull FilterChain filterChain
      ) throws ServletException, IOException {
            if (isWhiteListed(request.getServletPath())) {
                  filterChain.doFilter(request, response);
                  return;
            }

            final String authHeader = request.getHeader("Authorization");
            final String jwt;
            final String userEmail;

            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                  filterChain.doFilter(request, response);
                  return;
            }

            jwt = authHeader.substring(7);
            userEmail = jwtService.extractUsername(jwt);

            if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                  UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);
                  var isTokenValid = tokenRepository.findByToken(jwt)
                          .map(t -> !t.isExpired() && !t.isRevoked())
                          .orElse(false);
                  if (jwtService.isTokenValid(jwt, userDetails) && isTokenValid) {
                        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                                userDetails,
                                null,
                                userDetails.getAuthorities()
                        );
                        authToken.setDetails(
                                new WebAuthenticationDetailsSource().buildDetails(request)
                        );
                        SecurityContextHolder.getContext().setAuthentication(authToken);
                  }
            }

            filterChain.doFilter(request, response);
      }

      private boolean isWhiteListed(String servletPath) {
            return whiteListUrls.stream().anyMatch(url -> servletPath.matches(url.replace("**", ".*")));
      }
}
