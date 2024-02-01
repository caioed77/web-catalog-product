package com.catalogodeproduto.apicatalogo.repositories;

import com.catalogodeproduto.apicatalogo.entities.CorProdutoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CorProdutoRepository extends JpaRepository<CorProdutoEntity, Long> {

}
