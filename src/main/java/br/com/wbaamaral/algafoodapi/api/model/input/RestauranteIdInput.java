package br.com.wbaamaral.algafoodapi.api.model.input;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RestauranteIdInput {

	@NotNull
	private Long id;

}
