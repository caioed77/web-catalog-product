package com.catalogodeproduto.apicatalogo.domain.dto;

public record ChangePasswordDTO(String currentPassword, String newPassword, String confirmationPassword) {

}
