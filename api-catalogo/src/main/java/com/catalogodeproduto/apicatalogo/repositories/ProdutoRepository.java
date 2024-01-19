package com.catalogodeproduto.apicatalogo.repositories;


import com.catalogodeproduto.apicatalogo.entities.ProdutoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProdutoRepository extends JpaRepository<ProdutoEntity, Long> {

}
