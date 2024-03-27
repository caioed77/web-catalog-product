package com.catalogodeproduto.apicatalogo.services;


import com.catalogodeproduto.apicatalogo.dto.ProdutoDTO;
import com.catalogodeproduto.apicatalogo.dto.RetornaProdutoDTO;
import com.catalogodeproduto.apicatalogo.dto.RetornarCorProdutoDTO;
import com.catalogodeproduto.apicatalogo.entities.CorProdutoEntity;
import com.catalogodeproduto.apicatalogo.entities.ProdutoEntity;
import com.catalogodeproduto.apicatalogo.exceptions.RegrasDeNegocioException;
import com.catalogodeproduto.apicatalogo.exceptions.ResouceNotFoundException;
import com.catalogodeproduto.apicatalogo.repositories.CorProdutoRepository;
import com.catalogodeproduto.apicatalogo.repositories.ProdutoRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class ProdutoService {


      private final ProdutoRepository produtoRepository;

      private final CorProdutoRepository corProdutoRepository;

      private final PlatformTransactionManager transactionManager;


      public ProdutoService(ProdutoRepository produtoRepository, CorProdutoRepository corProdutoRepository, PlatformTransactionManager transactionManager) {
            this.produtoRepository = produtoRepository;
            this.corProdutoRepository = corProdutoRepository;
            this.transactionManager = transactionManager;
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
            var produto = new ProdutoEntity();
            var transaction = transactionManager.getTransaction(new DefaultTransactionDefinition());

           try {
                 produto.setId(produtoDTO.id());
                 produto.setDescricao(produtoDTO.descricao());
                 produto.setImagem(produtoDTO.imagem());
                 produto.setPrecoUnitario(produtoDTO.precoUnitario());
                 produto.setCores(produtoDTO.cores());

                 produtoRepository.save(produto);

                 for (var itemCor : produtoDTO.cores()) {
                       if (itemCor != null) {
                             corProdutoRepository.save(itemCor);
                       }
                 }

                 transactionManager.commit(transaction);
           } catch (Exception e) {
                 transactionManager.rollback(transaction);
                 throw new RegrasDeNegocioException("Erro ao gravar dados" + e.getMessage());
           }
      }

      public List<RetornaProdutoDTO> listaProdutos() {
            var produtos = produtoRepository.findAll();
            return produtos.stream().map(x ->
                    new RetornaProdutoDTO(
                            x.getId(),
                            x.getDescricao(),
                            x.getPrecoUnitario(),
                            x.getImagem(),
                            x.getCores()))
                    .toList();
      }

      @Transactional
      public void deletarProduto(Long id) {
            var produtoDelete = produtoRepository.findById(id).orElseThrow(() -> new ResouceNotFoundException(id));
            produtoRepository.delete(produtoDelete);
      }
}
