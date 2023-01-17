package br.com.wbaamaral.algafoodapi.domain.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.wbaamaral.algafoodapi.domain.model.Pedido;
import br.com.wbaamaral.algafoodapi.domain.service.EnvioEmailService.Mensagem;

@Service
public class FluxoPedidoService {

   @Autowired
   private EnvioEmailService envioEmail;
   
	@Autowired
	private EmissaoPedidoService emissaoPedido;

	@Transactional
	public void confirmar(String codigoPedido) {
		Pedido pedido = emissaoPedido.buscarOuFalhar(codigoPedido);

		pedido.confirmar();
		
		var mensagem = Mensagem.builder()
		      .assunto(pedido.getRestaurante().getNome() +  " - Pedido confirmado")
		      .corpo("O pedido de código <strong>" 
		            + pedido.getCodigo() + "</strong> foi confirmado!")
		      .destinatario(pedido.getCliente().getEmail())
		      .build();
		
		envioEmail.enviar(mensagem);
	}

	@Transactional
	public void cancelar(String codigoPedido) {
		Pedido pedido = emissaoPedido.buscarOuFalhar(codigoPedido);

		pedido.cancelar();
		;

	}

	@Transactional
	public void entregar(String codigoPedido) {
		Pedido pedido = emissaoPedido.buscarOuFalhar(codigoPedido);

		pedido.entregar();
	}
}