package br.com.wbaamaral.algafoodapi.api.model.input;

import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class EstadoInput {

	@ApiModelProperty(example = "Rondônia", required = true)
	@NotBlank
	private String nome;

}