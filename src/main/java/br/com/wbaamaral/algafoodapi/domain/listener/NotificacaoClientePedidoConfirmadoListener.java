package br.com.wbaamaral.algafoodapi.domain.listener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import br.com.wbaamaral.algafoodapi.domain.event.PedidoConfirmadoEvent;
import br.com.wbaamaral.algafoodapi.domain.model.Pedido;
import br.com.wbaamaral.algafoodapi.domain.service.EnvioEmailService;
import br.com.wbaamaral.algafoodapi.domain.service.EnvioEmailService.Mensagem;

@Component
public class NotificacaoClientePedidoConfirmadoListener {

	@Autowired
	private EnvioEmailService envioEmail;

	/*
	 * a mudança da anotação @EventListener para
	 * 
	 * @TransactionalEventListener, determina em que momento o evento será chamado,
	 * vamos deixar para depois do commit... isso dependerá da regra de negócio...
	 * com @EventListener pode ocorre um caso onde o evento foi disparado, porém o
	 * pedido não foi devidamente confirmado.
	 * 
	 * @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT) o default
	 * é after commit
	 *
	 */
	@TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
	public void aoConfirmarPedido(PedidoConfirmadoEvent event) {
		Pedido pedido = event.getPedido();

		var mensagem = Mensagem.builder().assunto(pedido.getRestaurante().getNome() + " - Pedido confirmado")
				.corpo("pedido-confirmado.html").variavel("pedido", pedido).destinatario(pedido.getCliente().getEmail())
				.build();

		envioEmail.enviar(mensagem);
	}
}
