package br.com.wbaamaral.algafoodapi.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.wbaamaral.algafoodapi.api.assembler.FormaPagamentoModelAssembler;
import br.com.wbaamaral.algafoodapi.api.model.FormaPagamentoModel;
import br.com.wbaamaral.algafoodapi.domain.model.Restaurante;
import br.com.wbaamaral.algafoodapi.domain.service.CadastroRestauranteService;

@RestController
@RequestMapping("/restaurantes/{restauranteId}/formas-pagamento")
public class RestauranteFormaPagamentoController {

	@Autowired
	private CadastroRestauranteService cadastroRestaurante;

	@Autowired
	private FormaPagamentoModelAssembler formaPagamentoModelAssembler;

	@GetMapping
	public List<FormaPagamentoModel> listar(@PathVariable Long restauranteId) {

		Restaurante restaurante = cadastroRestaurante.buscarOuFalhar(restauranteId);

		return formaPagamentoModelAssembler.toCollectionModel(restaurante.getFormasPagamento());
	}

	@DeleteMapping("/{formaPagamentoId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void desassociarFormaPagamento(@PathVariable Long restauranteId, @PathVariable Long formaPagamentoId) {

		cadastroRestaurante.desassociarFormaPagamento(restauranteId, formaPagamentoId);

	}

	@PutMapping("/{formaPagamentoId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void associarFormaPagamento(@PathVariable Long restauranteId, @PathVariable Long formaPagamentoId) {

		cadastroRestaurante.associarFormaPagamento(restauranteId, formaPagamentoId);

	}

}
