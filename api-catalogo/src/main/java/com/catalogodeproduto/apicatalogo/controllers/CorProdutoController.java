package com.catalogodeproduto.apicatalogo.controllers;

import com.catalogodeproduto.apicatalogo.dto.CorProdutoDTO;
import com.catalogodeproduto.apicatalogo.dto.RetornarCorProdutoDTO;
import com.catalogodeproduto.apicatalogo.services.CorProdutoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/cores")
public class CorProdutoController {
      
     private final CorProdutoService corProdutoService;
      
     public CorProdutoController(CorProdutoService corProdutoService) {
           this.corProdutoService = corProdutoService;
     } 
     
     
     @GetMapping(value = "/listagem")
     public ResponseEntity<List<RetornarCorProdutoDTO>> retornaCores() {
          return ResponseEntity.ok(corProdutoService.listarCores());
     }
}
