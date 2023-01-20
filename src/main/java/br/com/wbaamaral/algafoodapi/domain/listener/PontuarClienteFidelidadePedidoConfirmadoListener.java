package br.com.wbaamaral.algafoodapi.domain.listener;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import br.com.wbaamaral.algafoodapi.domain.event.PedidoConfirmadoEvent;

@Component
public class PontuarClienteFidelidadePedidoConfirmadoListener {

	/*
	 * Classe ilustrativa das possibilidades de uso do evento.
	 * a mudança da anotação @EventListener para 
	 * @TransactionalEventListener, determina em que momento o evento será 
	 * chamado, vamos deixar para depois do commit... isso dependerá da 
	 * regra de negócio... com @EventListener pode ocorre um caso onde 
	 * o evento foi disparado, porém o pedido não foi devidamente confirmado.
	 * @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
	 * o default é after commit
	 *
	 */	
	@TransactionalEventListener()
	public void pontuarAoConfirmarPedido(PedidoConfirmadoEvent event) {
		var nomeCliente = event.getPedido().getCliente().getNome();
		System.out.println("\n\nRecalculando pontuação para " + nomeCliente + "\n\n");
	}
}
