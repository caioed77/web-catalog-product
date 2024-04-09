package com.catalogodeproduto.apicatalogo.exceptions;

import java.security.SignatureException;

public class UsuarioNaoAutorizadoException extends SignatureException {
      public UsuarioNaoAutorizadoException(String message) {
            super(message);
      }
}
