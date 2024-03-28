package com.catalogodeproduto.apicatalogo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.SecurityBuilder;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.annotation.web.servlet.configuration.WebMvcSecurityConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class SecurityConfig extends Web {

      private final static  String corsOrigins = "http://localhost:5173";

      @Autowired
      private JwtAuthenticationEntryPointConfig jwtAuthenticationEntryPoint;

      @Autowired
      private UserDetailsService jwtUserDetailsService;

      @Autowired
      private JwtRequestFilterConfig jwtRequestFilter;

      @Autowired
      public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
            auth.userDetailsService(jwtUserDetailsService).passwordEncoder(passwordEncoder());
      }

      @Bean
      public PasswordEncoder passwordEncoder() {
            return new BCryptPasswordEncoder();
      }

      @Bean
      public WebMvcConfigurer corsConfigurer() {
            return new WebMvcConfigurer() {
                  @Override
                  public void addCorsMappings(CorsRegistry registry) {
                        registry.addMapping("/**").allowedMethods("*").allowedOrigins(corsOrigins);
                  }
            };
      }



}
