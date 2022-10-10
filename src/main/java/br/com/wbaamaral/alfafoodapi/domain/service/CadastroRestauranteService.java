package br.com.wbaamaral.alfafoodapi.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.wbaamaral.alfafoodapi.domain.exception.EntidadeNaoEncontradaException;
import br.com.wbaamaral.alfafoodapi.domain.model.Cozinha;
import br.com.wbaamaral.alfafoodapi.domain.model.Restaurante;
import br.com.wbaamaral.alfafoodapi.domain.repository.CozinhaRepository;
import br.com.wbaamaral.alfafoodapi.domain.repository.RestauranteRepository;

@Service
public class CadastroRestauranteService {

	@Autowired
	private RestauranteRepository restauranteRepository;
	
	@Autowired
	private CozinhaRepository cozinhaRepository;
	
	public Restaurante salvar(Restaurante restaurante) {
		Long cozinhaId = restaurante.getCozinha().getId();
		
		Cozinha cozinha = cozinhaRepository.findById(cozinhaId)
			.orElseThrow(() -> new EntidadeNaoEncontradaException(
					String.format("Não existe cadastro de cozinha com código %d", cozinhaId)));
		
		restaurante.setCozinha(cozinha);
		
		return restauranteRepository.save(restaurante);
	}
	
}
