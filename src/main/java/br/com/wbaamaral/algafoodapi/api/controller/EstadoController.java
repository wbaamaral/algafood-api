package br.com.wbaamaral.algafoodapi.api.controller;

import java.util.List;

import javax.transaction.Transactional;
import javax.validation.ConstraintDeclarationException;
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

import br.com.wbaamaral.algafoodapi.api.assembler.EstadoInputDisassembler;
import br.com.wbaamaral.algafoodapi.api.assembler.EstadoModelAssembler;
import br.com.wbaamaral.algafoodapi.api.model.EstadoModel;
import br.com.wbaamaral.algafoodapi.api.model.input.EstadoInput;
import br.com.wbaamaral.algafoodapi.api.openapi.controller.EstadoControllerOpenApi;
import br.com.wbaamaral.algafoodapi.domain.exception.EntidadeEmUsoException;
import br.com.wbaamaral.algafoodapi.domain.exception.NegocioException;
import br.com.wbaamaral.algafoodapi.domain.model.Estado;
import br.com.wbaamaral.algafoodapi.domain.repository.EstadoRepository;
import br.com.wbaamaral.algafoodapi.domain.service.CadastroEstadoService;

@RestController
@RequestMapping("/estados")
public class EstadoController implements EstadoControllerOpenApi{

	@Autowired
	private EstadoRepository estadoRepository;

	@Autowired
	private CadastroEstadoService cadastroEstado;

	@Autowired
	private EstadoModelAssembler estadoModelAssembler;

	@Autowired
	private EstadoInputDisassembler estadoInputDisassembler;

	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public List<EstadoModel> listar() {
		return estadoModelAssembler.toCollectionModel(estadoRepository.findAll());
	}

	@GetMapping(path = "/{estadoId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public EstadoModel buscar(@PathVariable Long estadoId) {
		Estado estado = cadastroEstado.buscarOuFalhar(estadoId);

		return estadoModelAssembler.toModel(estado);
	}

	@PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.CREATED)
	@Transactional
	public EstadoModel adicionar(@RequestBody @Valid EstadoInput estadoInput) {
		Estado estado = estadoInputDisassembler.toDomainObject(estadoInput);
		try {
			estado = cadastroEstado.salvar(estado);

			estadoRepository.flush();
		} catch (Exception e) {
			throw new NegocioException(e.getMessage());
		}

		return estadoModelAssembler.toModel(estado);
	}

	@PutMapping(path = "/{estadoId}", produces = MediaType.APPLICATION_JSON_VALUE)
	@Transactional
	public EstadoModel atualizar(@PathVariable Long estadoId, @RequestBody @Valid EstadoInput estadoInput) {
		try {

			Estado estadoAtual = cadastroEstado.buscarOuFalhar(estadoId);

			estadoInputDisassembler.copyToDomainObject(estadoInput, estadoAtual);

			estadoAtual = cadastroEstado.salvar(estadoAtual);
			estadoRepository.flush();

			return estadoModelAssembler.toModel(estadoAtual);
		} catch (ConstraintDeclarationException e) {

			throw new EntidadeEmUsoException("Estado está em uso, não é permitida a remoção.");

		} catch (Exception e) {

			throw new NegocioException(e.getMessage());
		}

	}

	@DeleteMapping("/{estadoId}")
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	@Transactional
	public void remover(@PathVariable Long estadoId) {
		try {

			cadastroEstado.excluir(estadoId);
			estadoRepository.flush();

		} catch (Exception e) {
			throw new NegocioException(e.getMessage());
		}
	}

}
