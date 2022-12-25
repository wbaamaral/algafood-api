package br.com.wbaamaral.algafoodapi.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.wbaamaral.algafoodapi.api.assembler.PedidoModelAssembler;
import br.com.wbaamaral.algafoodapi.api.assembler.PedidoResumoModelAssembler;
import br.com.wbaamaral.algafoodapi.api.model.PedidoModel;
import br.com.wbaamaral.algafoodapi.api.model.PedidoResumoModel;
import br.com.wbaamaral.algafoodapi.domain.model.Pedido;
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
   
   @GetMapping
   public List<PedidoResumoModel> listar() {

      List<Pedido> todosPedidos = pedidoRepository.findAll();

      return pedidoResumoModelAssembler.toCollectionModel(todosPedidos);
   }

   @GetMapping("/{pedidoId}")
   public PedidoModel buscar(@PathVariable Long pedidoId) {

      Pedido pedido = emissaoPedido.buscarOuFalhar(pedidoId);

      return pedidoModelAssembler.toModel(pedido);
   }
}
