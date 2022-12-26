package br.com.wbaamaral.algafoodapi.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.wbaamaral.algafoodapi.api.model.input.PedidoInput;
import br.com.wbaamaral.algafoodapi.domain.model.Pedido;

@Component
public class PedidoInputDisassembler {

   @Autowired
   private ModelMapper modelMapper;

   public Pedido toDomainObject(PedidoInput pedidoInput) {

      return modelMapper.map(pedidoInput, Pedido.class);
   }

   public void copyToDomainObject(PedidoInput pedidoInput, Pedido pedido) {

      modelMapper.map(pedidoInput, pedido);
   }
}