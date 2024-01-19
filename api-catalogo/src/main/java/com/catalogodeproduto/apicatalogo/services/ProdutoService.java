package com.catalogodeproduto.apicatalogo.services;


import com.catalogodeproduto.apicatalogo.dto.ProdutoDTO;
import com.catalogodeproduto.apicatalogo.entities.ProdutoEntity;
import com.catalogodeproduto.apicatalogo.repositories.ProdutoRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ProdutoService {


      public ProdutoRepository produtoRepository;


      public ProdutoService(ProdutoRepository produtoRepository) {
            this.produtoRepository = produtoRepository;
      }

      public ProdutoDTO retornarProdutoPorID(Long id) {
           var resultProduto = produtoRepository.findById(id).orElseThrow(() -> new RuntimeException("id do produto não encontrado"));
            return new ProdutoDTO(
                    resultProduto.getId(),
                    resultProduto.getDescricao(),
                    resultProduto.getPrecoUnitario(),
                    resultProduto.getImagem());
      }


      public Page<ProdutoDTO> retornarProdutos(Pageable pageable) {
            var result = produtoRepository.findAll(pageable);
            var resultDTO = result.map(x -> new ProdutoDTO(x.getId(), x.getDescricao(), x.getPrecoUnitario(), x.getImagem()));
            return resultDTO;
      }

      @Transactional
      public void gravarNovoProduto(ProdutoDTO produtoDTO) {
            var produtos = new ProdutoEntity(produtoDTO.id(), produtoDTO.descricao(), produtoDTO.precoUnitario(), produtoDTO.imagem());
            produtoRepository.save(produtos);
      }


}
