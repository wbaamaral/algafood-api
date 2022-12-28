package br.com.wbaamaral.algafoodapi.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.wbaamaral.algafoodapi.api.assembler.PedidoInputDisassembler;
import br.com.wbaamaral.algafoodapi.api.assembler.PedidoModelAssembler;
import br.com.wbaamaral.algafoodapi.api.assembler.PedidoResumoModelAssembler;
import br.com.wbaamaral.algafoodapi.api.model.PedidoModel;
import br.com.wbaamaral.algafoodapi.api.model.PedidoResumoModel;
import br.com.wbaamaral.algafoodapi.api.model.input.PedidoInput;
import br.com.wbaamaral.algafoodapi.domain.exception.EntidadeNaoEncontradaException;
import br.com.wbaamaral.algafoodapi.domain.exception.NegocioException;
import br.com.wbaamaral.algafoodapi.domain.model.Pedido;
import br.com.wbaamaral.algafoodapi.domain.model.Usuario;
import br.com.wbaamaral.algafoodapi.domain.repository.PedidoRepository;
import br.com.wbaamaral.algafoodapi.domain.service.EmissaoPedidoService;

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
   private PedidoInputDisassembler pedidoInputDisassembler;

   @GetMapping
   public List<PedidoResumoModel> listar() {

      List<Pedido> todosPedidos = pedidoRepository.findAll();

      return pedidoResumoModelAssembler.toCollectionModel(todosPedidos);
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
