package com.catalogodeproduto.apicatalogo.controllers;

import com.catalogodeproduto.apicatalogo.dto.ProdutoDTO;
import com.catalogodeproduto.apicatalogo.entities.ProdutoEntity;
import com.catalogodeproduto.apicatalogo.repositories.ProdutoRepository;
import com.catalogodeproduto.apicatalogo.services.ProdutoService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/produtos")
public class ProdutoController {

      private final ProdutoRepository produtoRepository;

      private final ProdutoService produtoService;

      public ProdutoController(ProdutoRepository produtoRepository, ProdutoService produtoService) {
            this.produtoRepository =  produtoRepository;
            this.produtoService = produtoService;
      }


      @PostMapping(value = "/gravarProduto")
      @ResponseStatus(code = HttpStatus.CREATED)
      public ResponseEntity<Void> novoProduto(@RequestBody ProdutoDTO produtoDTO) {
            produtoService.gravarNovoProduto(produtoDTO);
            return ResponseEntity.ok().build();
      }

      @GetMapping(value = "/retornarProduto/{id}")
      public ResponseEntity<ProdutoDTO> retornarProdutoID(@PathVariable Long id) {
            return ResponseEntity.ok(produtoService.retornarProdutoPorID(id));
      }

      @GetMapping(value = "/retornarProduto")
      public ResponseEntity<Page<ProdutoDTO>> retornarProdutosPaginado(Pageable pageable) {
            return ResponseEntity.ok(produtoService.retornarProdutos(pageable));
      }
}
