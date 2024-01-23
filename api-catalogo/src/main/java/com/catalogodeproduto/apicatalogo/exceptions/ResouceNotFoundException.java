package com.catalogodeproduto.apicatalogo.exceptions;

public class ResouceNotFoundException extends RuntimeException{
      public ResouceNotFoundException(Object id){
            super("não foi encontrado o registro com código "+ id);
      }
}
