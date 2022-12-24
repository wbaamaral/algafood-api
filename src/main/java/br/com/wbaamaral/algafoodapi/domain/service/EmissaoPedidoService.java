package br.com.wbaamaral.algafoodapi.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.wbaamaral.algafoodapi.domain.exception.PedidoNaoEncontradoException;
import br.com.wbaamaral.algafoodapi.domain.model.Pedido;
import br.com.wbaamaral.algafoodapi.domain.repository.PedidoRepository;

@Service
public class EmissaoPedidoService {

   @Autowired
   private PedidoRepository pedidoRepository;

   public Pedido buscarOuFalhar(Long pedidoId) {

      return pedidoRepository.findById(pedidoId).orElseThrow(() -> new PedidoNaoEncontradoException(pedidoId));
   }
}