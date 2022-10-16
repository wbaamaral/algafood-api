package br.com.wbaamaral.algafoodapi.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import br.com.wbaamaral.algafoodapi.domain.exception.CozinhaNaoEncontradaException;
import br.com.wbaamaral.algafoodapi.domain.exception.EntidadeEmUsoException;
import br.com.wbaamaral.algafoodapi.domain.model.Cozinha;
import br.com.wbaamaral.algafoodapi.domain.repository.CozinhaRepository;

@Service
public class CadastroCozinhaService {

	private static final String MSG_COZINHA_EM_USO = "Cozinha de código %d não pode ser removida, pois está em uso";

	private static final String MSG_COZINHA_NAO_EXISTE = "Não existe um cadastro de cozinha com código %d";

	@Autowired
	private CozinhaRepository cozinhaRepository;

	public Cozinha salvar(Cozinha cozinha) {
		return cozinhaRepository.save(cozinha);
	}

	public void excluir(Long cozinhaId) {
		try {
			cozinhaRepository.deleteById(cozinhaId);

		} catch (EmptyResultDataAccessException e) {
			throw new CozinhaNaoEncontradaException(String.format(MSG_COZINHA_NAO_EXISTE, cozinhaId));

		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(String.format(MSG_COZINHA_EM_USO, cozinhaId));
		}
	}

	public Cozinha buscarOuFalhar(Long cozinhaId) {
		return cozinhaRepository.findById(cozinhaId).orElseThrow(
				() -> new CozinhaNaoEncontradaException(String.format(MSG_COZINHA_NAO_EXISTE, cozinhaId)));
	}
}
