package com.catalogodeproduto.apicatalogo.controllers.exception;

import com.catalogodeproduto.apicatalogo.exceptions.RegrasDeNegocioException;
import com.catalogodeproduto.apicatalogo.exceptions.ResouceNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ResourceExceptionHandler {

      @ExceptionHandler(RegrasDeNegocioException.class)
      public ResponseEntity<StandardError> objectNotFound(RegrasDeNegocioException e, HttpServletRequest request) {
            HttpStatus status = HttpStatus.BAD_REQUEST;
            StandardError err = new StandardError(System.currentTimeMillis(), status.value(), "Recurso Já Existe", e.getMessage(), request.getRequestURI());

            return ResponseEntity.status(status).body(err);
      }


      @ExceptionHandler(ResouceNotFoundException.class)
      public ResponseEntity<StandardError> resourceAlreadyExists(ResouceNotFoundException e, HttpServletRequest request) {
            HttpStatus status = HttpStatus.NOT_FOUND;
            StandardError err = new StandardError(System.currentTimeMillis(), status.value(), "Não Encontrado", e.getMessage(), request.getRequestURI());

            return ResponseEntity.status(status).body(err);

      }
}