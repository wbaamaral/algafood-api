package br.com.wbaamaral.algafoodapi.api.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.wbaamaral.algafoodapi.api.model.FormaPagamentoModel;
import br.com.wbaamaral.algafoodapi.domain.model.FormaPagamento;

@Component
public class FormaPagamentoModelAssembler {

	@Autowired
	private ModelMapper modelMapper;

	public FormaPagamentoModel toModel(FormaPagamento formaPagamento) {

		return modelMapper.map(formaPagamento, FormaPagamentoModel.class);
	}

	public List<FormaPagamentoModel> toCollectionModel(List<FormaPagamento> formasPagamentos) {

		return formasPagamentos.stream().map(formaPagamento -> toModel(formaPagamento)).collect(Collectors.toList());
	}
}
