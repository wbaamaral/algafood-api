package br.com.wbaamaral.algafoodapi.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.wbaamaral.algafoodapi.api.model.input.FormaPagamentoInput;
import br.com.wbaamaral.algafoodapi.domain.model.FormaPagamento;

@Component
public class FormaPagamentoInputDisassembler {

	@Autowired
	private ModelMapper modelMapper;

	public FormaPagamento toDomainObject(FormaPagamentoInput formapagamentoInput) {

		return modelMapper.map(formapagamentoInput, FormaPagamento.class);
	}

	public void copytoDomainObject(FormaPagamentoInput formaPagamentoInput, FormaPagamento formaPagamento) {

		modelMapper.map(formaPagamentoInput, formaPagamento);
	}

}
