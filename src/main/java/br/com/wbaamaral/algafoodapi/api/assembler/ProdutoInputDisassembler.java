package br.com.wbaamaral.algafoodapi.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.wbaamaral.algafoodapi.api.model.input.ProdutoInput;
import br.com.wbaamaral.algafoodapi.domain.model.Produto;

@Component
public class ProdutoInputDisassembler {

	@Autowired
	private ModelMapper modelMapper;

	public Produto toDomainObject(ProdutoInput produtoInput) {

		return modelMapper.map(produtoInput, Produto.class);
	}

	public void copyToDomainObject(ProdutoInput produtoInput, Produto produto) {

		modelMapper.map(produtoInput, produto);
	}
}
