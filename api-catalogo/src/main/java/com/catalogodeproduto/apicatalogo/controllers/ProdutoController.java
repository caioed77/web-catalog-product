package com.catalogodeproduto.apicatalogo.controllers;

import com.catalogodeproduto.apicatalogo.domain.dto.ProdutoDTO;
import com.catalogodeproduto.apicatalogo.domain.dto.RetornaProdutoDTO;
import com.catalogodeproduto.apicatalogo.services.ProdutoService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/produtos")
public class ProdutoController {

      private final ProdutoService produtoService;

      public ProdutoController(ProdutoService produtoService) {
            this.produtoService = produtoService;
      }

      @PostMapping(value = "/gravarProduto")
      @ResponseStatus(code = HttpStatus.CREATED)
      public ResponseEntity<Void> novoProduto(@RequestBody ProdutoDTO produtoDTO) {
            produtoService.gravarNovoProduto(produtoDTO);
            return ResponseEntity.ok().build();
      }

      @GetMapping(value = "/retornarProduto/{id}")
      public ResponseEntity<RetornaProdutoDTO> retornarProdutoID(@PathVariable Long id) {
            return ResponseEntity.ok(produtoService.retornarProdutoPorID(id));
      }

      @GetMapping(value = "/retornarProduto")
      public ResponseEntity<Page<RetornaProdutoDTO>> retornarProdutosPaginado(Pageable pageable) {
            return ResponseEntity.ok(produtoService.retornarProdutos(pageable));
      }

      @GetMapping(value = "/listagemProdutos")
      public ResponseEntity<List<RetornaProdutoDTO>> retornaTodosProdutos() {
            return ResponseEntity.ok(produtoService.listaProdutos());
      }

      @DeleteMapping(value = "/deletarProduto/{id}")
      @ResponseStatus(HttpStatus.NO_CONTENT)
      public ResponseEntity<Void> deletarProdutos(@PathVariable Long id) {
            produtoService.deletarProduto(id);
            return ResponseEntity.ok().build();
      }
}
