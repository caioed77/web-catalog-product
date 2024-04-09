package com.catalogodeproduto.apicatalogo.dto;

import com.catalogodeproduto.apicatalogo.entities.enumEntities.RoleEnum;

public record UsuarioDTO(String email, String senha, RoleEnum role) {
}
