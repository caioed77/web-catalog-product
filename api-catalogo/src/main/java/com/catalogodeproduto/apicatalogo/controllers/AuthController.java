package com.catalogodeproduto.apicatalogo.controllers;


import com.catalogodeproduto.apicatalogo.config.JwtTokenUtilConfig;
import com.catalogodeproduto.apicatalogo.dto.JwtTokenDTO;
import com.catalogodeproduto.apicatalogo.dto.UsuarioDTO;
import com.catalogodeproduto.apicatalogo.services.JwtUserDetailsService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping(value = "/auth")
public class AuthController {

   private final AuthenticationManager authenticationManager;
   private final JwtUserDetailsService userDetailsService;
   private final JwtTokenUtilConfig jwtTokenUtilConfig;

   public AuthController(AuthenticationManager authenticationManager, JwtTokenUtilConfig jwtTokenUtilConfig, JwtUserDetailsService userDetailsService){
       this.authenticationManager = authenticationManager;
       this.jwtTokenUtilConfig = jwtTokenUtilConfig;
       this.userDetailsService = userDetailsService;
   }

    @PostMapping(value = "/authenticate")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody UsuarioDTO authenticationRequest) throws Exception {
        authenticate(authenticationRequest.email(), authenticationRequest.senha());
        final UserDetails userDetails = userDetailsService
                .loadUserByUsername(authenticationRequest.email());
        final String token = jwtTokenUtilConfig.generateToken(userDetails);
        return ResponseEntity.ok(new JwtTokenDTO(token));
    }


    private void authenticate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }


}
