package com.catalogodeproduto.apicatalogo.services;


import com.catalogodeproduto.apicatalogo.dto.CorProdutoDTO;
import com.catalogodeproduto.apicatalogo.dto.RetornarCorProdutoDTO;
import com.catalogodeproduto.apicatalogo.entities.CorProdutoEntity;
import com.catalogodeproduto.apicatalogo.exceptions.ResouceNotFoundException;
import com.catalogodeproduto.apicatalogo.repositories.CorProdutoRepository;
import com.catalogodeproduto.apicatalogo.repositories.ProdutoRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CorProdutoService {

      private final ProdutoRepository produtoRepository;

      private final CorProdutoRepository corProdutoRepository;


      public CorProdutoService(ProdutoRepository produtoRepository, CorProdutoRepository corProdutoRepository) {
            this.produtoRepository = produtoRepository;
            this.corProdutoRepository = corProdutoRepository;
      }

      @Transactional
      public void gravarNovaCor(CorProdutoDTO corProdutoDTO) {
           var produto = produtoRepository.findById(Long.valueOf(corProdutoDTO.produtoId())).orElseThrow(() -> new ResouceNotFoundException(corProdutoDTO.produtoId()));
           var novaCor = new  CorProdutoEntity(corProdutoDTO.id(), corProdutoDTO.descricao(), corProdutoDTO.imagem(), produto);
           corProdutoRepository.save(novaCor);
      }

      public List<RetornarCorProdutoDTO> listarCores() {
            var cores = corProdutoRepository.findAll();
            return cores.stream().map(x -> new RetornarCorProdutoDTO(x.getId(), x.getDescricao(), x.getImagem())).collect(Collectors.toList());
      }




}
