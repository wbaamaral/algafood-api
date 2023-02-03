package br.com.wbaamaral.algafoodapi.api.model.input;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class EnderecoInput {

	@ApiModelProperty(example = "76824-312", required = true)
	@NotBlank
	private String cep;
  
	@ApiModelProperty(example = "Rua Florinda", required = true)
	@NotBlank
	private String logradouro;
   
	@ApiModelProperty(example = "1818", required = true)
	@NotBlank
	private String numero;
   
	@ApiModelProperty(example = "Sobrado 01")
	private String complemento;
   
	@ApiModelProperty(example = "Quintana", required = true)
	@NotBlank
	private String bairro;
   
	@Valid
	@NotNull
	private CidadeIdInput cidade;

}