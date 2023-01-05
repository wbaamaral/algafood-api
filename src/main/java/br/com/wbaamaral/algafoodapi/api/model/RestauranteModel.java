package br.com.wbaamaral.algafoodapi.api.model;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonView;

import br.com.wbaamaral.algafoodapi.api.model.view.RestauranteView;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RestauranteModel {

   @JsonView({ RestauranteView.Resumo.class, RestauranteView.ApenasNome.class })
	private Long id;
   
   @JsonView({ RestauranteView.Resumo.class, RestauranteView.ApenasNome.class })
	private String nome;
   
   @JsonView(RestauranteView.Resumo.class)
	private BigDecimal taxaFrete;
   @JsonView(RestauranteView.Resumo.class)
	private CozinhaModel cozinha;
   
	private Boolean ativo;
	private Boolean aberto;
	private EnderecoModel endereco;
}
