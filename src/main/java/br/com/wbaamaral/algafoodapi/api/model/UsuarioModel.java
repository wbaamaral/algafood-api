package br.com.wbaamaral.algafoodapi.api.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UsuarioModel {

	@ApiModelProperty(example = "1")
	private Long id;

	@ApiModelProperty(example = "Josué da Silva")
	private String nome;

	@ApiModelProperty(example = "josue@email.com.br")
	private String email;
}