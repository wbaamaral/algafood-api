package br.com.wbaamaral.algafoodapi.api.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class EnderecoModel {

	@ApiModelProperty(example = "78600-000")
	private String cep;

	@ApiModelProperty(example = "Rua Floriano Peixoto")
	private String logradouro;

	@ApiModelProperty(example = "\"1800\"")
	private String numero;

	@ApiModelProperty(example = "Apto 901")
	private String complemento;

	@ApiModelProperty(example = "Centro")
	private String bairro;
	
	private CidadeResumoModel cidade;

}