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

import br.com.wbaamaral.algafoodapi.api.assembler.CidadeInputDisassembler;
import br.com.wbaamaral.algafoodapi.api.assembler.CidadeModelAssembler;
import br.com.wbaamaral.algafoodapi.api.model.CidadeModel;
import br.com.wbaamaral.algafoodapi.api.model.input.CidadeInput;
import br.com.wbaamaral.algafoodapi.api.openapi.model.OpenApiDocCidadeController;
import br.com.wbaamaral.algafoodapi.domain.exception.EstadoNaoEncontradaException;
import br.com.wbaamaral.algafoodapi.domain.exception.EstadoNaoEncontradoException;
import br.com.wbaamaral.algafoodapi.domain.exception.NegocioException;
import br.com.wbaamaral.algafoodapi.domain.model.Cidade;
import br.com.wbaamaral.algafoodapi.domain.repository.CidadeRepository;
import br.com.wbaamaral.algafoodapi.domain.service.CadastroCidadeService;
import io.swagger.annotations.Api;

@Api(tags = "Cidades")
@RestController
@RequestMapping(path = "/cidades")
public class CidadeController implements OpenApiDocCidadeController {

   @Autowired
   private CidadeRepository cidadeRepository;

   @Autowired
   private CadastroCidadeService cadastroCidade;

   @Autowired
   private CidadeModelAssembler cidadeModelAssembler;

   @Autowired
   private CidadeInputDisassembler cidadeInputDisassembler;

   @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
   public List<CidadeModel> listar() {

      List<Cidade> todasCidades = cidadeRepository.findAll();

      return cidadeModelAssembler.toCollectionModel(todasCidades);
   }

   @GetMapping(path =  "/{cidadeId}", produces = MediaType.APPLICATION_JSON_VALUE)
   public CidadeModel buscar(@PathVariable Long cidadeId) {

      Cidade cidade = cadastroCidade.buscarOuFalhar(cidadeId);

      return cidadeModelAssembler.toModel(cidade);
   }

   @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
   @ResponseStatus(code = HttpStatus.CREATED)
   public CidadeModel adicionar(@RequestBody @Valid CidadeInput cidadeInput) {

      try {
         Cidade cidade = cidadeInputDisassembler.toDomainObject(cidadeInput);

         cidade = cadastroCidade.salvar(cidade);

         return cidadeModelAssembler.toModel(cidade);
      } catch (EstadoNaoEncontradoException e) {
         
         throw new NegocioException(e.getMessage(), e);
      }

   }

   @PutMapping( path = "/{cidadeId}", produces = MediaType.APPLICATION_JSON_VALUE )
   public CidadeModel atualizar(@PathVariable Long cidadeId, @RequestBody @Valid CidadeInput cidadeInput) {

      try {
         Cidade cidadeAtual = cadastroCidade.buscarOuFalhar(cidadeId);

         cidadeInputDisassembler.copyToDomainObject(cidadeInput, cidadeAtual);

         cidadeAtual = cadastroCidade.salvar(cidadeAtual);

         return cidadeModelAssembler.toModel(cidadeAtual);

      } catch (EstadoNaoEncontradaException e) {

         throw new NegocioException(e.getMessage());
      }
   }

   @DeleteMapping(path = "/{cidadeId}", produces = {})
   @ResponseStatus(HttpStatus.NO_CONTENT)
   public void remover(@PathVariable Long cidadeId) {

      cadastroCidade.excluir(cidadeId);
   }

}
