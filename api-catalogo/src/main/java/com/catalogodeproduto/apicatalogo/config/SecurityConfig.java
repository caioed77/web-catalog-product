package com.catalogodeproduto.apicatalogo.config;

import com.catalogodeproduto.apicatalogo.config.jwtMiddleware.JwtAuthFilter;
import com.catalogodeproduto.apicatalogo.services.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import static com.catalogodeproduto.apicatalogo.entities.enumEntities.PermissionEnum.ADMIN_CREATE;
import static com.catalogodeproduto.apicatalogo.entities.enumEntities.PermissionEnum.ADMIN_DELETE;
import static com.catalogodeproduto.apicatalogo.entities.enumEntities.PermissionEnum.ADMIN_READ;
import static com.catalogodeproduto.apicatalogo.entities.enumEntities.PermissionEnum.ADMIN_UPDATE;
import static com.catalogodeproduto.apicatalogo.entities.enumEntities.PermissionEnum.MANAGER_CREATE;
import static com.catalogodeproduto.apicatalogo.entities.enumEntities.PermissionEnum.MANAGER_DELETE;
import static com.catalogodeproduto.apicatalogo.entities.enumEntities.PermissionEnum.MANAGER_READ;
import static com.catalogodeproduto.apicatalogo.entities.enumEntities.PermissionEnum.MANAGER_UPDATE;
import static com.catalogodeproduto.apicatalogo.entities.enumEntities.RoleEnum.ADMIN;
import static com.catalogodeproduto.apicatalogo.entities.enumEntities.RoleEnum.MANAGER;
import static org.springframework.http.HttpMethod.DELETE;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.http.HttpMethod.PUT;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity
public class SecurityConfig {

      private final static  String corsOrigins = "http://localhost:5173";
      private static final String[] WHITE_LIST_URL = {"/api/v1/auth/**",
              "/v2/api-docs",
              "/v3/api-docs",
              "/v3/api-docs/**",
              "/swagger-resources",
              "/swagger-resources/**",
              "/configuration/ui",
              "/configuration/security",
              "/swagger-ui/**",
              "/webjars/**",
              "/swagger-ui.html"};

      @Autowired
      private JwtAuthFilter jwtAuthFilter;
      @Autowired
      private  AuthenticationProvider authenticationProvider;
      @Autowired
      private UsuarioService userService;
      @Autowired
      private LogoutHandler logoutHandler;


      @Bean
      public WebMvcConfigurer corsConfigurer() {
            return new WebMvcConfigurer() {
                  @Override
                  public void addCorsMappings(CorsRegistry registry) {
                        registry.addMapping("/**").allowedMethods("*").allowedOrigins(corsOrigins);
                  }
            };
      }

      @Bean
      public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
            http
                    .csrf(AbstractHttpConfigurer::disable)
                    .authorizeHttpRequests(req ->
                            req.requestMatchers(WHITE_LIST_URL)
                                    .permitAll()
                                    .requestMatchers("/api/v1/management/**").hasAnyRole(ADMIN.name(), MANAGER.name())
                                    .requestMatchers(GET, "/api/v1/management/**").hasAnyAuthority(ADMIN_READ.name(), MANAGER_READ.name())
                                    .requestMatchers(POST, "/api/v1/management/**").hasAnyAuthority(ADMIN_CREATE.name(), MANAGER_CREATE.name())
                                    .requestMatchers(PUT, "/api/v1/management/**").hasAnyAuthority(ADMIN_UPDATE.name(), MANAGER_UPDATE.name())
                                    .requestMatchers(DELETE, "/api/v1/management/**").hasAnyAuthority(ADMIN_DELETE.name(), MANAGER_DELETE.name())
                                    .anyRequest()
                                    .authenticated()
                    )
                    .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                    .authenticationProvider(authenticationProvider)
                    .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                    .logout(logout ->
                            logout.logoutUrl("/api/v1/auth/logout")
                                    .addLogoutHandler(logoutHandler)
                                    .logoutSuccessHandler((request, response, authentication) -> SecurityContextHolder.clearContext())
                    )
            ;

            return http.build();
      }

}
