package br.com.wbaamaral.algafoodapi.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.wbaamaral.algafoodapi.domain.exception.RestauranteNaoEncontradaException;
import br.com.wbaamaral.algafoodapi.domain.model.Restaurante;
import br.com.wbaamaral.algafoodapi.domain.repository.RestauranteRepository;

@Service
public class CadastroRestauranteService {

	@Autowired
	private RestauranteRepository restauranteRepository;

	@Autowired
	private CadastroCozinhaService cadastroCozinha;

	public Restaurante salvar(Restaurante restaurante) {

		restaurante.setCozinha(cadastroCozinha.buscarOuFalhar(restaurante.getCozinha().getId()));

		return restauranteRepository.save(restaurante);
	}

	public Restaurante buscarOuFalhar(Long restauranteId) {
		return restauranteRepository.findById(restauranteId)
				.orElseThrow(() -> new RestauranteNaoEncontradaException(restauranteId));
	}
}
