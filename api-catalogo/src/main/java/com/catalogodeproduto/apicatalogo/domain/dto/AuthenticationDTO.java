package com.catalogodeproduto.apicatalogo.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record AuthenticationDTO(
        @JsonProperty("access_token")
        String accessToken,
        @JsonProperty("refresh_Token")
        String refreshToken,
        UsuarioDTO user) {
}
