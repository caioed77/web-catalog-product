package com.catalogodeproduto.apicatalogo.repositories;

import com.catalogodeproduto.apicatalogo.domain.projections.ProdutoCorProjection;
import com.catalogodeproduto.apicatalogo.entities.CorProdutoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CorProdutoRepository extends JpaRepository<CorProdutoEntity, Long> {

      @Query(nativeQuery = true, value = """              
              SELECT cor_produto.id as idCor, cor_produto.descricao as descricaoCor, produtos.descricao as descricaoProduto, cor_produto.imagem as imagemProduto FROM produto_cor
                   inner join cor_produto on cor_produto.id = produto_cor.cor_id
                   inner join produtos on produtos.id = produto_cor.produto_id
              where produto_cor.produto_id = :produto                                
              """)
      List<ProdutoCorProjection> listarCorProduto(Long produto);
}
