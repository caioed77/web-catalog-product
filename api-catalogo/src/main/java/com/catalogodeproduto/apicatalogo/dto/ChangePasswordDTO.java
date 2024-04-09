package com.catalogodeproduto.apicatalogo.dto;

public record ChangePasswordDTO(String currentPassword, String newPassword, String confirmationPassword) {

}
