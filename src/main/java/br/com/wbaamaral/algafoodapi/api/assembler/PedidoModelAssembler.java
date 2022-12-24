package br.com.wbaamaral.algafoodapi.api.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.wbaamaral.algafoodapi.api.model.PedidoModel;
import br.com.wbaamaral.algafoodapi.domain.model.Pedido;

@Component
public class PedidoModelAssembler {

   @Autowired
   private ModelMapper modelMapper;

   public PedidoModel toModel(Pedido pedido) {

      return modelMapper.map(pedido, PedidoModel.class);
   }

   public List<PedidoModel> toCollectionModel(List<Pedido> pedidos) {

      // @formatter:off

      return pedidos.stream()
            .map(pedido -> toModel(pedido))
            .collect(Collectors.toList());
      
      // @formatter:on
   }

}