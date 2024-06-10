package com.catalogodeproduto.apicatalogo.services;


import com.catalogodeproduto.apicatalogo.dto.CorProdutoDTO;
import com.catalogodeproduto.apicatalogo.dto.RetornarCorProdutoDTO;
import com.catalogodeproduto.apicatalogo.entities.CorProdutoEntity;
import com.catalogodeproduto.apicatalogo.exceptions.ResouceNotFoundException;
import com.catalogodeproduto.apicatalogo.repositories.CorProdutoRepository;
import com.catalogodeproduto.apicatalogo.repositories.ProdutoRepository;
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

      public List<RetornarCorProdutoDTO> listarCores() {
            var cores = corProdutoRepository.findAll();
            return cores.stream().map(x -> new RetornarCorProdutoDTO(x.getId(), x.getDescricao(), x.getImagem())).collect(Collectors.toList());
      }

      public void GravarCor(Long produtoId, List<CorProdutoDTO> corProdutoDTO) {
            var produto = produtoRepository.findById(produtoId)
                    .orElseThrow(() -> new ResouceNotFoundException(produtoId));

            List<CorProdutoEntity> corProduto = corProdutoDTO.stream()
                    .map(dto -> {
                          CorProdutoEntity entity = new CorProdutoEntity();
                          entity.setDescricao(dto.descricao());
                          entity.setImagem(dto.imagem());
                          return entity;
                    })
                    .map(corProdutoRepository::save)
                    .collect(Collectors.toList());

            produto.setCores(corProduto);
            produtoRepository.save(produto);
      }
}
