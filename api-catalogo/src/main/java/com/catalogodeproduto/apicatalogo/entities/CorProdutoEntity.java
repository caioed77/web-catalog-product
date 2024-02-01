package com.catalogodeproduto.apicatalogo.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

      @JsonIgnore
      @ManyToOne
      @JoinColumn(name = "PRODUTO_ID")
      public ProdutoEntity produtoId;
}
