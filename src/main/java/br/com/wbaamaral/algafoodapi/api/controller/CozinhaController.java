package br.com.wbaamaral.algafoodapi.api.controller;

import java.util.List;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.wbaamaral.algafoodapi.api.assembler.CozinhaInputDisassembler;
import br.com.wbaamaral.algafoodapi.api.assembler.CozinhaModelAssembler;
import br.com.wbaamaral.algafoodapi.api.model.CozinhaModel;
import br.com.wbaamaral.algafoodapi.api.model.input.CozinhaInput;
import br.com.wbaamaral.algafoodapi.domain.exception.EntidadeEmUsoException;
import br.com.wbaamaral.algafoodapi.domain.exception.NegocioException;
import br.com.wbaamaral.algafoodapi.domain.model.Cozinha;
import br.com.wbaamaral.algafoodapi.domain.repository.CozinhaRepository;
import br.com.wbaamaral.algafoodapi.domain.service.CadastroCozinhaService;

@RestController
@RequestMapping(value = "/cozinhas")
public class CozinhaController {

	@Autowired
	private CozinhaRepository cozinhaRepository;

	@Autowired
	private CadastroCozinhaService cadastroCozinha;

	@Autowired
	private CozinhaModelAssembler cozinhaModelAssembler;

	@Autowired
	private CozinhaInputDisassembler cozinhaInputDisassembler;

	@GetMapping
	public List<Cozinha> listar() {
		return cozinhaRepository.findAll();
	}

	@GetMapping("/{cozinhaId}")
	public CozinhaModel buscar(@PathVariable Long cozinhaId) {

		return cozinhaModelAssembler.toModel(cadastroCozinha.buscarOuFalhar(cozinhaId));

	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	@Transactional
	public CozinhaModel adicionar(@RequestBody @Valid CozinhaInput cozinhaInput) {
		try {
			Cozinha cozinha = cozinhaInputDisassembler.toDomainObject(cozinhaInput);

			CozinhaModel cozinhaModel = cozinhaModelAssembler.toModel(cadastroCozinha.salvar(cozinha));

			cozinhaRepository.flush();

			return cozinhaModel;
		} catch (Exception e) {
			throw new NegocioException(e.getMessage());
		}
	}

	@PutMapping("/{cozinhaId}")
	@Transactional
	public CozinhaModel atualizar(@PathVariable Long cozinhaId, @RequestBody CozinhaInput cozinhaInput) {
		try {
			Cozinha cozinhaAntiga = cozinhaInputDisassembler.toDomainObject(cozinhaInput);

			Cozinha cozinhaAtual = cadastroCozinha.buscarOuFalhar(cozinhaId);
			BeanUtils.copyProperties(cozinhaAntiga, cozinhaAtual, "id");

			Cozinha cozinhaFinal = cadastroCozinha.salvar(cozinhaAtual);

			cozinhaRepository.flush();

			return cozinhaModelAssembler.toModel(cozinhaFinal);
		} catch (Exception e) {
			throw new NegocioException(e.getMessage());
		}
	}

	@DeleteMapping("/{cozinhaId}")
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	@Transactional
	public void remover(@PathVariable Long cozinhaId) {
		try {
			cadastroCozinha.excluir(cozinhaId);

			cozinhaRepository.flush();
		} catch (DataIntegrityViolationException e) {

			throw new EntidadeEmUsoException("A cozinha esta em uso, não pode ser removida.");

		} catch (Exception e) {

			throw new NegocioException(e.getMessage());
		}
	}

}
