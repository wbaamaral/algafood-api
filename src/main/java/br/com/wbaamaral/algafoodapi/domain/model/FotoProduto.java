package br.com.wbaamaral.algafoodapi.domain.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class FotoProduto {

   @EqualsAndHashCode.Include
   @Id
   @Column(name = "produto_id")
   private Long id;

   @OneToOne(fetch = FetchType.LAZY)
   @MapsId
   private Produto produto;

   private String nomeArquivo;
   private String descricao;
   private String contentType;
   private Long tamanho;

   public Long getIdRestaurante() {

      Long retorno = null;

      if (getProduto() != null) {

         retorno = getProduto().getRestaurante().getId();
      }

      return retorno;
   }

   public Long getIdProduto() {

      Long retorno = null;

      if (getProduto() != null) {

         retorno = getProduto().getId();
      }

      return retorno;
   }
}
