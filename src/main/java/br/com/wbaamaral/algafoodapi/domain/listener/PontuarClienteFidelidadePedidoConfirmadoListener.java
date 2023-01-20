package br.com.wbaamaral.algafoodapi.domain.listener;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import br.com.wbaamaral.algafoodapi.domain.event.PedidoConfirmadoEvent;

@Component
public class PontuarClienteFidelidadePedidoConfirmadoListener {

	//classe ilustrativa das possibilidades de uso do evento.
	@EventListener
	public void pontuarAoConfirmarPedido(PedidoConfirmadoEvent event) {
		var nomeCliente = event.getPedido().getCliente().getNome();
		System.out.println("\n\nRecalculando pontuação para " + nomeCliente + "\n\n");
	}
}
