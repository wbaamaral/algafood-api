package br.com.wbaamaral.algafoodapi.api.model;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class RestauranteModel {

  private Long id;
  private String nome;
  private BigDecimal taxaFrete;
  private CozinhaModel cozinha;
  
}
