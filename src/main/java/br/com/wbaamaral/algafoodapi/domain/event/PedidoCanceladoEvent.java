package br.com.wbaamaral.algafoodapi.domain.event;

import br.com.wbaamaral.algafoodapi.domain.model.Pedido;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PedidoCanceladoEvent {

	private Pedido pedido;
}
