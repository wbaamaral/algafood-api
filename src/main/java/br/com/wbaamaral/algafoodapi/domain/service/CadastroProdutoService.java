package br.com.wbaamaral.algafoodapi.domain.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.wbaamaral.algafoodapi.domain.exception.ProdutoNaoEncontradoException;
import br.com.wbaamaral.algafoodapi.domain.model.Produto;
import br.com.wbaamaral.algafoodapi.domain.repository.ProdutoRepository;

@Service
public class CadastroProdutoService {

	@Autowired
	private ProdutoRepository produtoRepository;

	@Transactional
	public Produto salvar(Produto produto) {
		return produtoRepository.save(produto);
	}

	public Produto buscarOuFalhar(Long restauranteId, Long produtoId) {
		return produtoRepository.findById(restauranteId, produtoId)
				.orElseThrow(() -> new ProdutoNaoEncontradoException(restauranteId, produtoId));
	}
}