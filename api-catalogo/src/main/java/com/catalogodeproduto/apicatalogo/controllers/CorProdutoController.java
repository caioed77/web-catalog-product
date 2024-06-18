package com.catalogodeproduto.apicatalogo.controllers;

import com.catalogodeproduto.apicatalogo.domain.dto.CorProdutoDTO;
import com.catalogodeproduto.apicatalogo.domain.dto.RetornarCorProdutoDTO;
import com.catalogodeproduto.apicatalogo.services.CorProdutoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/cores")
public class CorProdutoController {
      
     private final CorProdutoService corProdutoService;
      
     public CorProdutoController(CorProdutoService corProdutoService) {
           this.corProdutoService = corProdutoService;
     }


     @GetMapping(value = "/listagem")
     public ResponseEntity<List<CorProdutoDTO>> retornaCores() {
          return ResponseEntity.ok(corProdutoService.listarCores());
     }

     @PostMapping(value = "{produtoId}/criarCorProduto")
     public ResponseEntity<Void> criarNovaCorProduto(@PathVariable Long produtoId, @RequestBody List<CorProdutoDTO> corProdutoDTO) {
          corProdutoService.GravarCor(produtoId, corProdutoDTO);
          return  ResponseEntity.status(HttpStatus.CREATED).build();
     }

     @GetMapping(value = "/produtoCor/{produtoId}")
     public ResponseEntity<List<RetornarCorProdutoDTO>> listagemCorProduto(@PathVariable Long produtoId) {
          return ResponseEntity.ok(corProdutoService.listaCorProduto(produtoId));
     }
}
