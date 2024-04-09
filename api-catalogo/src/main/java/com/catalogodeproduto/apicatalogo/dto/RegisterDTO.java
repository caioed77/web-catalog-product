package com.catalogodeproduto.apicatalogo.dto;

import com.catalogodeproduto.apicatalogo.entities.enumEntities.RoleEnum;

public record RegisterDTO(String email, String senha, RoleEnum role) {
}
