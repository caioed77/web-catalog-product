package com.catalogodeproduto.apicatalogo.services;


import com.catalogodeproduto.apicatalogo.domain.dto.CorProdutoDTO;
import com.catalogodeproduto.apicatalogo.domain.dto.RetornarCorProdutoDTO;
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

      public List<CorProdutoDTO> listarCores() {
            var cores = corProdutoRepository.findAll();
            return cores.stream().map(x -> new CorProdutoDTO(x.getId(), x.getDescricao(), x.getImagem())).collect(Collectors.toList());
      }

      public void GravarCor(Long produtoId, List<CorProdutoDTO> corProdutoDTO) {
            var produto = produtoRepository.findById(produtoId)
                    .orElseThrow(() -> new ResouceNotFoundException(produtoId));

            List<CorProdutoEntity> novasCores = corProdutoDTO.stream()
                    .map(dto -> {
                          CorProdutoEntity entity = new CorProdutoEntity();
                          entity.setDescricao(dto.descricao());
                          entity.setImagem(dto.imagem());
                          return entity;
                    })
                    .map(corProdutoRepository::save)
                    .collect(Collectors.toList());

            List<CorProdutoEntity>coresExistentes = produto.getCores();

            if (coresExistentes == null || coresExistentes.isEmpty()) {
                  coresExistentes = novasCores;
            } else {
                  coresExistentes.addAll(novasCores);
            }

            produto.setCores(coresExistentes);
            produtoRepository.save(produto);
      }

      public List<RetornarCorProdutoDTO> listaCorProduto(Long produtoId) {
            var produtoCor = corProdutoRepository.listarCorProduto(produtoId);

            return produtoCor.stream()
                    .map(x -> new RetornarCorProdutoDTO(
                                    x.getIdCor(),
                                    x.getDescricaoCor(),
                                    x.getDescricaoProduto(),
                                    x.getImagemProduto()))
                    .toList();
      }
}
