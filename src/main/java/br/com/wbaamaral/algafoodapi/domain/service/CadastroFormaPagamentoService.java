package br.com.wbaamaral.algafoodapi.domain.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import br.com.wbaamaral.algafoodapi.domain.exception.EntidadeEmUsoException;
import br.com.wbaamaral.algafoodapi.domain.exception.FormaPagamentoNaoEncontradaException;
import br.com.wbaamaral.algafoodapi.domain.model.FormaPagamento;
import br.com.wbaamaral.algafoodapi.domain.repository.FormaPagamentoRepository;

@Service
public class CadastroFormaPagamentoService {

	private static final String MSG_FORMA_PAGAMENTO_EM_USO = "Forma de pagamentode código %d não pode ser removida, pois esta em uso";

	@Autowired
	private FormaPagamentoRepository formaPagamentoRepository;

	@Transactional
	public FormaPagamento salvar(FormaPagamento formaPagamento) {

		return formaPagamentoRepository.save(formaPagamento);
	}

	@Transactional
	public void excluir(Long formaPagamentoId) {
		try {

			formaPagamentoRepository.deleteById(formaPagamentoId);
			formaPagamentoRepository.flush();

		} catch (EmptyResultDataAccessException e) {

			throw new FormaPagamentoNaoEncontradaException(formaPagamentoId);

		} catch (DataIntegrityViolationException e) {

			throw new EntidadeEmUsoException(String.format(MSG_FORMA_PAGAMENTO_EM_USO, formaPagamentoId));
		}
	}

	public FormaPagamento buscarOuFalhar(Long formaPagamentoId) {

		return formaPagamentoRepository.findById(formaPagamentoId)
				.orElseThrow(() -> new FormaPagamentoNaoEncontradaException(formaPagamentoId));
	}
}
