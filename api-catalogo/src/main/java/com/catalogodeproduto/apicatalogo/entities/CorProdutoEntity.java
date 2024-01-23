package com.catalogodeproduto.apicatalogo.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "COR_PRODUTO")
public class CorProdutoEntity {

      @Id
      @GeneratedValue(strategy = GenerationType.IDENTITY)
      public Long id;

      public String descricao;

      @Column(length = 500)
      public String imagem;

      @ManyToOne
      public ProdutoEntity produtoId;
}
