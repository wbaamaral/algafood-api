package br.com.wbaamaral.algafoodapi.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;

import br.com.wbaamaral.algafoodapi.api.assembler.PedidoInputDisassembler;
import br.com.wbaamaral.algafoodapi.api.assembler.PedidoModelAssembler;
import br.com.wbaamaral.algafoodapi.api.assembler.PedidoModelAssemblerJsonFilter;
import br.com.wbaamaral.algafoodapi.api.assembler.PedidoResumoModelAssembler;
import br.com.wbaamaral.algafoodapi.api.model.PedidoModel;
import br.com.wbaamaral.algafoodapi.api.model.PedidoResumoModel;
import br.com.wbaamaral.algafoodapi.api.model.PedidoResumoModelJsonFilter;
import br.com.wbaamaral.algafoodapi.api.model.input.PedidoInput;
import br.com.wbaamaral.algafoodapi.domain.exception.EntidadeNaoEncontradaException;
import br.com.wbaamaral.algafoodapi.domain.exception.NegocioException;
import br.com.wbaamaral.algafoodapi.domain.model.Pedido;
import br.com.wbaamaral.algafoodapi.domain.model.Usuario;
import br.com.wbaamaral.algafoodapi.domain.repository.PedidoRepository;
import br.com.wbaamaral.algafoodapi.domain.repository.filter.PedidoFilter;
import br.com.wbaamaral.algafoodapi.domain.service.EmissaoPedidoService;
import br.com.wbaamaral.algafoodapi.infrastructure.repository.spec.PedidoSpecs;

@RestController
@RequestMapping(value = "/pedidos")
public class PedidoController {

   @Autowired
   private PedidoRepository pedidoRepository;

   @Autowired
   private EmissaoPedidoService emissaoPedido;

   @Autowired
   private PedidoModelAssembler pedidoModelAssembler;

   @Autowired
   private PedidoResumoModelAssembler pedidoResumoModelAssembler;

   @Autowired
   private PedidoModelAssemblerJsonFilter pedidoResumoModelAssemblerJ;

   @Autowired
   private PedidoInputDisassembler pedidoInputDisassembler;

   @GetMapping
   public List<PedidoResumoModel> pesquisar(PedidoFilter filtro) {

      List<Pedido> todosPedidos = pedidoRepository.findAll(PedidoSpecs.usandoFiltro(filtro));

      return pedidoResumoModelAssembler.toCollectionModel(todosPedidos);
   }


   @GetMapping("/aula-13-2")
   public MappingJacksonValue listar(@RequestParam(required = false) String campos) {
      List<Pedido> pedidos = pedidoRepository.findAll();
      List<PedidoResumoModelJsonFilter> pedidosModel = pedidoResumoModelAssemblerJ.toCollectionModel(pedidos);  
      
      MappingJacksonValue pedidosWrapper = new MappingJacksonValue(pedidosModel);
      
      SimpleFilterProvider filterProvider = new SimpleFilterProvider();
      filterProvider.addFilter("pedidoFilter", SimpleBeanPropertyFilter.serializeAll());
      
      if (StringUtils.isNotBlank(campos)) {
         filterProvider.addFilter("pedidoFilter", SimpleBeanPropertyFilter.filterOutAllExcept(campos.split(",")));
      }
      
      pedidosWrapper.setFilters(filterProvider);
      
      return pedidosWrapper;
   }
   
   @GetMapping("/{codigoPedido}")
   public PedidoModel buscar(@PathVariable String codigoPedido) {

      Pedido pedido = emissaoPedido.buscarOuFalhar(codigoPedido);

      return pedidoModelAssembler.toModel(pedido);
   }

   @PostMapping
   @ResponseStatus(HttpStatus.CREATED)
   public PedidoModel adicionar(@Valid @RequestBody PedidoInput pedidoInput) {

      try {
         Pedido novoPedido = pedidoInputDisassembler.toDomainObject(pedidoInput);

         // TODO pegar usuário autenticado
         novoPedido.setCliente(new Usuario());
         novoPedido.getCliente().setId(1L);

         novoPedido = emissaoPedido.emitir(novoPedido);

         return pedidoModelAssembler.toModel(novoPedido);
      } catch (EntidadeNaoEncontradaException e) {
         throw new NegocioException(e.getMessage(), e);
      }
   }
}
