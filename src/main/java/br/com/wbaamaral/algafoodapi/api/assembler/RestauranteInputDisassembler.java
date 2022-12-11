package br.com.wbaamaral.algafoodapi.api.assembler;

import org.springframework.stereotype.Component;

import br.com.wbaamaral.algafoodapi.api.model.RestauranteModel;
import br.com.wbaamaral.algafoodapi.api.model.input.CozinhaInput;
import br.com.wbaamaral.algafoodapi.api.model.input.RestauranteInput;
import br.com.wbaamaral.algafoodapi.domain.model.Cozinha;
import br.com.wbaamaral.algafoodapi.domain.model.Restaurante;

@Component
public class RestauranteInputDisassembler {

  public Restaurante toDomainObject(RestauranteInput restauranteInput) {

    Restaurante restaurante = new Restaurante();
    Cozinha cozinha = new Cozinha();

    cozinha.setId(restauranteInput.getCozinha().getId());

    restaurante.setNome(restauranteInput.getNome());
    restaurante.setTaxaFrete(restauranteInput.getTaxaFrete());
    restaurante.setCozinha(cozinha);

    return restaurante;
  }
  
  public RestauranteInput toInputObInput (Restaurante restaurante) {

	    RestauranteInput restauranteInput = new RestauranteInput();
	    CozinhaInput cozinha = new CozinhaInput();

	    cozinha.setId(restaurante.getCozinha().getId());

	    restauranteInput.setNome(restaurante.getNome());
	    restauranteInput.setTaxaFrete(restaurante.getTaxaFrete());
	    restauranteInput.setCozinha(cozinha);

	    return restauranteInput;

  }
}
