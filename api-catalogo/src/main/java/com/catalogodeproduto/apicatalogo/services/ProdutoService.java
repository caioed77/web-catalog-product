package com.catalogodeproduto.apicatalogo.services;


import com.catalogodeproduto.apicatalogo.dto.ProdutoDTO;
import com.catalogodeproduto.apicatalogo.dto.RetornaProdutoDTO;
import com.catalogodeproduto.apicatalogo.dto.RetornarCorProdutoDTO;
import com.catalogodeproduto.apicatalogo.entities.ProdutoEntity;
import com.catalogodeproduto.apicatalogo.exceptions.ResouceNotFoundException;
import com.catalogodeproduto.apicatalogo.repositories.CorProdutoRepository;
import com.catalogodeproduto.apicatalogo.repositories.ProdutoRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProdutoService {


      public final ProdutoRepository produtoRepository;

      public final CorProdutoRepository corProdutoRepository;


      public ProdutoService(ProdutoRepository produtoRepository, CorProdutoRepository corProdutoRepository) {
            this.produtoRepository = produtoRepository;
            this.corProdutoRepository = corProdutoRepository;
      }

      public RetornaProdutoDTO retornarProdutoPorID(Long id) {
           var resultProduto = produtoRepository.findById(id).orElseThrow(() -> new ResouceNotFoundException(id));
            return new RetornaProdutoDTO(
                    resultProduto.getId(),
                    resultProduto.getDescricao(),
                    resultProduto.getPrecoUnitario(),
                    resultProduto.getImagem(),
                    resultProduto.getCores());
      }


      public Page<RetornaProdutoDTO> retornarProdutos(Pageable pageable) {
            var result = produtoRepository.findAll(pageable);
            return result.map(x -> new RetornaProdutoDTO(x.getId(), x.getDescricao(), x.getPrecoUnitario(), x.getImagem(), x.getCores()));
      }

      @Transactional
      public void gravarNovoProduto(ProdutoDTO produtoDTO) {
            produtoRepository.save(new ProdutoEntity(
                    produtoDTO.id(),
                    produtoDTO.descricao(),
                    produtoDTO.precoUnitario(),
                    produtoDTO.imagem(),
                    null
                    )
            );
      }

      public List<RetornaProdutoDTO> listaProdutos() {
            var produtos = produtoRepository.findAll();
            return produtos.stream().map(x -> new RetornaProdutoDTO(x.getId(), x.getDescricao(), x.getPrecoUnitario(), x.getImagem(), x.getCores())).collect(Collectors.toList());
      }

      @Transactional
      public void deletarProduto(Long id) {
            var produtoDelete = produtoRepository.findById(id).orElseThrow(() -> new ResouceNotFoundException(id));
            produtoRepository.delete(produtoDelete);
      }
}
