package br.com.wbaamaral.algafoodapi.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.wbaamaral.algafoodapi.domain.model.FotoProduto;
import br.com.wbaamaral.algafoodapi.domain.repository.ProdutoRepository;

@Service
public class CatalogoFotoProdutoService {

	@Autowired
	private ProdutoRepository produtoRepository;
	
	@Transactional
	public FotoProduto salvar(FotoProduto foto) {
	
		return produtoRepository.save(foto);
	}
}
