package br.com.wbaamaral.algafoodapi.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
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

import br.com.wbaamaral.algafoodapi.api.assembler.GrupoInputDisassembler;
import br.com.wbaamaral.algafoodapi.api.assembler.GrupoModelAssembler;
import br.com.wbaamaral.algafoodapi.api.model.GrupoModel;
import br.com.wbaamaral.algafoodapi.api.model.input.GrupoInput;
import br.com.wbaamaral.algafoodapi.api.openapi.controller.GrupoControllerOpenApi;
import br.com.wbaamaral.algafoodapi.domain.model.Grupo;
import br.com.wbaamaral.algafoodapi.domain.repository.GrupoRepository;
import br.com.wbaamaral.algafoodapi.domain.service.CadastroGrupoService;

@RestController
@RequestMapping("/grupos")
public class GrupoController implements GrupoControllerOpenApi  {

	@Autowired
	private GrupoRepository repository;

	@Autowired
	private CadastroGrupoService service;

	@Autowired
	private GrupoModelAssembler assembler;

	@Autowired
	private GrupoInputDisassembler disassembler;

	@GetMapping
	public List<GrupoModel> listar() {

		List<Grupo> todosGrupos = repository.findAll();

		return assembler.toCollectionModel(todosGrupos);
	}

	@GetMapping("/{grupoId}")
	public GrupoModel buscar(@PathVariable Long grupoId) {

		Grupo grupo = service.buscarOuFalhar(grupoId);

		return assembler.toModel(grupo);
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public GrupoModel adicionar(@RequestBody @Valid GrupoInput grupoInput) {

		Grupo grupo = disassembler.toDomainObject(grupoInput);

		grupo = service.salvar(grupo);

		return assembler.toModel(grupo);
	}

	@PutMapping("/{grupoId}")
	public GrupoModel atualizar(@PathVariable Long grupoId, @RequestBody @Valid GrupoInput grupoInput) {

		Grupo grupoAtual = service.buscarOuFalhar(grupoId);

		disassembler.copyToDomainObject(grupoInput, grupoAtual);

		grupoAtual = service.salvar(grupoAtual);

		repository.flush();

		return assembler.toModel(grupoAtual);
	}

	@DeleteMapping("/{grupoId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long grupoId) {

		service.excluir(grupoId);
	}

}
