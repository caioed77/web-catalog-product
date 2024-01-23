package com.catalogodeproduto.apicatalogo.entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;


@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "PRODUTOS")
public class ProdutoEntity {

      @Id
      @GeneratedValue(strategy = GenerationType.IDENTITY)
      private Long id;

      private String descricao;

      private Double precoUnitario;

      @Column(length = 500)
      private String imagem;

      @OneToMany(mappedBy = "produtoId")
      public List<CorProdutoEntity> cores = new ArrayList<>();

}
