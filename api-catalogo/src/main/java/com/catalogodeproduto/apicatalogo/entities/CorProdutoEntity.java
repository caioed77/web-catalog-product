package com.catalogodeproduto.apicatalogo.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "COR_PRODUTO")
public class CorProdutoEntity {

      @Id
      @GeneratedValue(strategy = GenerationType.IDENTITY)
      private Long id;

      private String descricao;

      @Column(length = 500)
      private String imagem;

      @JsonIgnore
      @ManyToMany(mappedBy = "cores")
      private List<ProdutoEntity> produtos;
}
