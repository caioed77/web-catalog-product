package com.catalogodeproduto.apicatalogo.domain.dto;

import com.catalogodeproduto.apicatalogo.entities.CorProdutoEntity;

import java.util.List;

public record ProdutoDTO(Long id, String descricao, Double precoUnitario, String imagem, List<CorProdutoEntity> cores) {


}
