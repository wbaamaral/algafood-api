package br.com.wbaamaral.algafoodapi.api.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.wbaamaral.algafoodapi.api.model.ProdutoModel;
import br.com.wbaamaral.algafoodapi.domain.model.Produto;

@Component
public class ProdutoModelAssembler {

	@Autowired
	private ModelMapper modelMapper;

	public ProdutoModel toModel(Produto produto) {

		return modelMapper.map(produto, ProdutoModel.class);
	}

	public List<ProdutoModel> toCollectionModel(List<Produto> produtos) {

		/// @formatter:off
		return produtos.stream()
				.map(produto -> toModel(produto))
				.collect(Collectors.toList());
		// @formatter:on
	}
}
