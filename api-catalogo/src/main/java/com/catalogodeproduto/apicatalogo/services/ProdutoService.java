package com.catalogodeproduto.apicatalogo.services;


import com.catalogodeproduto.apicatalogo.dto.ProdutoDTO;
import com.catalogodeproduto.apicatalogo.entities.CorProdutoEntity;
import com.catalogodeproduto.apicatalogo.entities.ProdutoEntity;
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

      public ProdutoDTO retornarProdutoPorID(Long id) {
           var resultProduto = produtoRepository.findById(id).orElseThrow(() -> new RuntimeException("id do produto não encontrado"));
            return new ProdutoDTO(
                    resultProduto.getId(),
                    resultProduto.getDescricao(),
                    resultProduto.getPrecoUnitario(),
                    resultProduto.getImagem(),
                    resultProduto.getCores());
      }


      public Page<ProdutoDTO> retornarProdutos(Pageable pageable) {
            var result = produtoRepository.findAll(pageable);
            var resultDTO = result.map(x -> new ProdutoDTO(x.getId(), x.getDescricao(), x.getPrecoUnitario(), x.getImagem(), x.getCores()));
            return resultDTO;
      }

      @Transactional
      public void gravarNovoProduto(ProdutoDTO produtoDTO) {
            var produtos = new ProdutoEntity(produtoDTO.id(), produtoDTO.descricao(), produtoDTO.precoUnitario(), produtoDTO.imagem(), produtoDTO.cores());

            var cores = new CorProdutoEntity();

            for (var itemCor : produtos.cores) {
               cores.setId(itemCor.getId());
               cores.setDescricao(itemCor.getDescricao());
               cores.setImagem(itemCor.getImagem());
               cores.setProdutoId(itemCor.getProdutoId());
            }

            corProdutoRepository.save(cores);
            produtoRepository.save(produtos);
      }

      public List<ProdutoDTO> listaProdutos() {
         var lista =   produtoRepository.findAll();
         var listaDTO = lista.stream().map(x -> new ProdutoDTO(x.getId(), x.getDescricao(), x.getPrecoUnitario(), x.getImagem(), x.getCores()));
         return listaDTO.toList();
      }
}
