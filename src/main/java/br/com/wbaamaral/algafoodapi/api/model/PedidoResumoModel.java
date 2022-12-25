package br.com.wbaamaral.algafoodapi.api.model;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PedidoResumoModel {

   private Long id;
   private BigDecimal subtotal;
   private BigDecimal taxaFrete;
   private BigDecimal valorTotal;
   private String status;
   private OffsetDateTime dataCriacao;
   private RestauranteResumoModel restaurante;
   private UsuarioModel cliente;
}