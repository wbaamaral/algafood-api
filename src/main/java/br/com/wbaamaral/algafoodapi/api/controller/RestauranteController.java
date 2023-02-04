package br.com.wbaamaral.algafoodapi.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;

import br.com.wbaamaral.algafoodapi.api.assembler.RestauranteInputDisassembler;
import br.com.wbaamaral.algafoodapi.api.assembler.RestauranteModelAssembler;
import br.com.wbaamaral.algafoodapi.api.model.RestauranteModel;
import br.com.wbaamaral.algafoodapi.api.model.input.RestauranteInput;
import br.com.wbaamaral.algafoodapi.api.model.view.RestauranteView;
import br.com.wbaamaral.algafoodapi.api.openapi.controller.RestauranteControllerOpenApi;
import br.com.wbaamaral.algafoodapi.domain.exception.CidadeNaoEncontradaException;
import br.com.wbaamaral.algafoodapi.domain.exception.CozinhaNaoEncontradaException;
import br.com.wbaamaral.algafoodapi.domain.exception.NegocioException;
import br.com.wbaamaral.algafoodapi.domain.exception.RestauranteNaoEncontradoException;
import br.com.wbaamaral.algafoodapi.domain.model.Restaurante;
import br.com.wbaamaral.algafoodapi.domain.repository.RestauranteRepository;
import br.com.wbaamaral.algafoodapi.domain.service.CadastroRestauranteService;

@RestController
@RequestMapping(path = "/restaurantes")
public class RestauranteController  implements RestauranteControllerOpenApi{

	@Autowired
	private RestauranteRepository restauranteRepository;
	
	@Autowired
	private CadastroRestauranteService cadastroRestaurante;
	
	@Autowired
	private RestauranteModelAssembler restauranteModelAssembler;
	
	@Autowired
	private RestauranteInputDisassembler restauranteInputDisassembler;

	@JsonView(RestauranteView.Resumo.class)
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public List<RestauranteModel> listar() {
		return restauranteModelAssembler.toCollectionModel(restauranteRepository.findAll());
	}

	@JsonView(RestauranteView.ApenasNome.class)
	@GetMapping(params = "projecao=apenas-nome", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<RestauranteModel> listarApenasNomes() {
		return listar();
	}
	
	@GetMapping(value = "/{restauranteId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public RestauranteModel buscar(@PathVariable Long restauranteId) {
		Restaurante restaurante = cadastroRestaurante.buscarOuFalhar(restauranteId);
		
		return restauranteModelAssembler.toModel(restaurante);
	}
	
	@PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.CREATED)
	public RestauranteModel adicionar(@RequestBody @Valid RestauranteInput restauranteInput) {
		try {
			Restaurante restaurante = restauranteInputDisassembler.toDomainObject(restauranteInput);
			
			return restauranteModelAssembler.toModel(cadastroRestaurante.salvar(restaurante));
		} catch (CozinhaNaoEncontradaException | CidadeNaoEncontradaException e) {
			throw new NegocioException(e.getMessage());
		}
	}
	
	@PutMapping("/{restauranteId}")
	public RestauranteModel atualizar(@PathVariable Long restauranteId,
			@RequestBody @Valid RestauranteInput restauranteInput) {
		try {
			Restaurante restauranteAtual = cadastroRestaurante.buscarOuFalhar(restauranteId);
			
			restauranteInputDisassembler.copyToDomainObject(restauranteInput, restauranteAtual);
			
			return restauranteModelAssembler.toModel(cadastroRestaurante.salvar(restauranteAtual));
		} catch (CozinhaNaoEncontradaException | CidadeNaoEncontradaException e) {
			throw new NegocioException(e.getMessage());
		}
	}
	
	@PutMapping("/{restauranteId}/ativo")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void ativar(@PathVariable Long restauranteId) {
		cadastroRestaurante.ativar(restauranteId);
	}
	
	@DeleteMapping("/{restauranteId}/ativo")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void inativar(@PathVariable Long restauranteId) {
		cadastroRestaurante.inativar(restauranteId);
	}
	
	@PutMapping("/ativacoes")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void ativarMultiplos(@RequestBody List<Long> restauranteIds) {
		try {
			cadastroRestaurante.ativar(restauranteIds);
		} catch (RestauranteNaoEncontradoException e) {
			throw new NegocioException(e.getMessage(), e);
		}
	}
	
	@DeleteMapping("/ativacoes")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void inativarMultiplos(@RequestBody List<Long> restauranteIds) {
		try {
			cadastroRestaurante.inativar(restauranteIds);
		} catch (RestauranteNaoEncontradoException e) {
			throw new NegocioException(e.getMessage(), e);
		}
	}

	@PutMapping("/{restauranteId}/abertura")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void abrir(@PathVariable Long restauranteId) {
		cadastroRestaurante.abrir(restauranteId);
	}
	
	@PutMapping("/{restauranteId}/fechamento")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void fechar(@PathVariable Long restauranteId) {
		cadastroRestaurante.fechar(restauranteId);
	}
}
