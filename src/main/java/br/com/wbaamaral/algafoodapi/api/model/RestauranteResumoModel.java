package br.com.wbaamaral.algafoodapi.api.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RestauranteResumoModel {

	@ApiModelProperty(example = "1")
	private Long id;

	@ApiModelProperty(example = "Churrascaria Triângulo Mineiro")
	private String nome;
}